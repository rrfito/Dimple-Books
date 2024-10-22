package com.example.dimplebooks.UI.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewpager2.widget.ViewPager2
import bookHistoryViewModel
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.BannerAdapter
import com.example.dimplebooks.UI.adapters.bookHistoryAdapter
import com.example.dimplebooks.UI.adapters.newestBookAdapter
import com.example.dimplebooks.data.AppDatabase
import com.example.dimplebooks.data.entity.bookHistory
import com.example.dimplebooks.model.BookResponse

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
    private lateinit var historyBookList: ArrayList<bookHistory>
    private lateinit var adapterr : newestBookAdapter
    private lateinit var viewModel: BookViewModel
    private lateinit var viewModelHistory: bookHistoryViewModel



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
        //image banner
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

        //viewmodel newewst book
        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        viewModel.newestBooks.observe(viewLifecycleOwner) { books ->
            adapterr.updateBookList(books)
        }
        viewModel.getNewestBooks()


        //history recycleview
        val recycleViewHistory = view.findViewById<RecyclerView>(R.id.recycleviewHistory)

        recycleViewHistory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        historyBookList = ArrayList()
        val adapter = bookHistoryAdapter(historyBookList)
        recycleViewHistory.adapter = adapter
        Log.d("GetStartedFragment", "Adapter set with ${historyBookList.size} items")

        //view model history book database
        viewModelHistory = ViewModelProvider(requireActivity()).get(bookHistoryViewModel::class.java)
        Log.d("GetStartedFragment", "ViewModel initialized")
        viewModelHistory.bookHistory.observe(viewLifecycleOwner) { history ->
            Log.d("GetStartedFragment", "Data observed from ViewModel")
            historyBookList.clear()
            historyBookList.addAll(history)
            Log.d("GetStartedFragment", "HistoryBookList updated: ${historyBookList?.size} items")
            adapter.notifyDataSetChanged()
            Log.d("GetStartedFragment", "Adapter notified of data change")
        }
        Log.d("GetStartedFragment", "Fetching book history from database")
        viewModelHistory.getBookHistory()

        return view
    }

}