package com.example.dimplebooks.UI.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewpager2.widget.ViewPager2
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.BannerAdapter
import com.example.dimplebooks.UI.adapters.bookHistoryAdapter
import com.example.dimplebooks.UI.adapters.newestBookAdapter
import com.example.dimplebooks.model.BookResponse
import com.example.dimplebooks.model.RecycleViewBookHistory
import com.example.dimplebooks.model.bookModel
import com.example.dimplebooks.retrofit.apiService
import com.example.dimplebooks.viewModel.BookViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [History.newInstance] factory method to
 * create an instance of this fragment.
 */
class History : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var newestBookList: ArrayList<bookModel>
    private lateinit var adapterr : newestBookAdapter
    private lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val imageList = listOf(R.drawable.banner, R.drawable.banner, R.drawable.banner)
        val adapterrr = BannerAdapter(imageList)
        viewPager.adapter = adapterrr

        //newest book recycleview
        val recycleViewNew = view.findViewById<RecyclerView>(R.id.Released)
        recycleViewNew.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         newestBookList = ArrayList()
         adapterr = newestBookAdapter(newestBookList)
        recycleViewNew.adapter = adapterr


        Log.d("adapter","${adapterr.itemCount}")

        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        viewModel.newestBooks.observe(viewLifecycleOwner) { books ->
            adapterr.updateBookList(books)
        }
        viewModel.getNewestBooks()


        return view
    }

}