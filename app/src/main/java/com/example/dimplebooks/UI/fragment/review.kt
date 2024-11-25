package com.example.dimplebooks.UI.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.reviewAdapter
import com.example.dimplebooks.databinding.FragmentReviewBinding
import com.example.dimplebooks.data.model.reviewModel


class review : Fragment() {
    private var _binding : FragmentReviewBinding? = null
    private val binding get() = _binding

    private lateinit var commentAdapter: reviewAdapter
    private val commentList = listOf(
        reviewModel(R.drawable.profile, "John Doe", "Lorem Ipsum is simply dummy text of the printing and typesetting industry.", "22 hours ago"),
        reviewModel(R.drawable.profile, "Jane Smith", "Lorem Ipsum has been the industry's standard dummy text.", "3 days ago"),
        reviewModel(R.drawable.profile, "Alice Brown", "It is a long established fact that a reader will be distracted.", "1 week ago"),
        reviewModel(R.drawable.profile, "Kanap Brown", "It is a long established fact that a reader will be distracted.", "2 week ago")

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentReviewBinding.inflate(inflater, container, false)


        binding?.recycleviewRecom?.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        commentAdapter = reviewAdapter(commentList)
        binding?.recycleviewRecom?.adapter = commentAdapter


        return binding?.root
    }



}