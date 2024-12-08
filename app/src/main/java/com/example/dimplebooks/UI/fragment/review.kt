package com.example.dimplebooks.UI.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.reviewAdapter
import com.example.dimplebooks.UI.viewModel.ReviewViewModel
import com.example.dimplebooks.data.model.ShowsReview
import com.example.dimplebooks.data.network.RetrofitInstance
import com.example.dimplebooks.data.repository.reviewRepository
import com.example.dimplebooks.databinding.FragmentReviewBinding
import com.example.dimplebooks.utils.Resource
import com.example.dimplebooks.utils.ViewModelFactoryReview

class review : Fragment(), reviewAdapter.onItemClickListener {

    private lateinit var binding: FragmentReviewBinding
    private lateinit var viewmodel: ReviewViewModel
    private lateinit var commentAdapter: reviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout
        binding = FragmentReviewBinding.inflate(inflater, container, false)

        // Initialize ViewModel and Repository
        val factory = ViewModelFactoryReview(reviewRepository(RetrofitInstance.ReviewApi))
        viewmodel = ViewModelProvider(this, factory).get(ReviewViewModel::class.java)

        // Setup RecyclerView
        setupRecyclerView()

        // Fetch and observe reviews
        getReview()

        return binding.root
    }

    private fun setupRecyclerView() {
        commentAdapter = reviewAdapter(mutableListOf(),this)
        binding.recycleviewRecom.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = commentAdapter
        }
    }

    private fun getReview() {
        viewmodel.getReviews(requireContext())
        viewmodel.data.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Empty ->{
                    binding.recycleviewRecom.visibility = View.GONE
                    binding.state.root.visibility = View.VISIBLE
                    binding.state.stateText.text = "this book has no reviews yet"
                    binding.state.stateButton.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.recycleviewRecom.visibility = View.GONE
                    binding.state.root.visibility = View.VISIBLE
                    binding.state.stateText.text = "There seems to be an error, please refresh first."
                    binding.state.stateButton.setOnClickListener{
                        viewmodel.getReviews(requireContext(),true)
                    }
                }
                is Resource.Loading ->{
                    binding.recycleviewRecom.visibility = View.GONE
                    binding.loadingstate.root.visibility = View.VISIBLE
                    binding.state.root.visibility = View.GONE

                }
                is Resource.Success -> {
                    binding.recycleviewRecom.visibility = View.VISIBLE
                    binding.loadingstate.root.visibility = View.GONE
                    binding.state.root.visibility = View.GONE

                    val reviews = resource.data?.items?.map {
                        ShowsReview(
                            username = it.username,
                            imageUrl = it.imageUrl,
                            comment = it.comment,
                            dateTime = it.dateTime,
                            rating = it.rating,
                            bookTitle = it.bookTitle,
                            _uuid = it._uuid
                        )
                    } ?: listOf()
                    commentAdapter.updateData(reviews)
                }
            }
        }
    }

    override fun onItemClick(position: Int) {
        val reviewToDelete = commentAdapter.commentList[position]
        viewmodel.deleteReviews(requireContext(), reviewToDelete._uuid.toString())
        Log.d("delete", "delete: ${reviewToDelete._uuid}")
        viewmodel.deleteReview.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Deleting review...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Review deleted successfully", Toast.LENGTH_SHORT).show()
                    getReview()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Failed to delete review: ${resource.message}", Toast.LENGTH_SHORT).show()
                    Log.d("error", "error: ${resource.message}")
                }
                is Resource.Empty -> TODO()
            }
        }
    }

}