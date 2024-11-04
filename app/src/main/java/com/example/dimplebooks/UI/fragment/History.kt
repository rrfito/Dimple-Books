package com.example.dimplebooks.UI.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.BannerAdapter
import com.example.dimplebooks.UI.adapters.bookHistoryAdapter
import com.example.dimplebooks.UI.adapters.newestBookAdapter
import com.example.dimplebooks.UI.activity.detailBook
import com.example.dimplebooks.data.AppDatabase
import com.example.dimplebooks.data.dao.bookHistoryDao
import com.example.dimplebooks.data.entity.bookHistoryEntity

import com.example.dimplebooks.model.bookModel
import com.example.dimplebooks.viewModel.BookViewModel
import com.example.dimplebooks.viewModel.historyBookViewModel
import com.example.dimplebooks.viewModel.historyBookViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [History.newInstance] factory method to
 * create an instance of this fragment.
 */
class History : Fragment(),bookHistoryAdapter.OnItemClickListener,newestBookAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var newestBookList: ArrayList<bookModel>
    private lateinit var historyBookList: ArrayList<bookHistoryEntity>
    private lateinit var adapterr : newestBookAdapter
    private lateinit var historyAdapter: bookHistoryAdapter
    private lateinit var viewModel: BookViewModel
    private lateinit var viewModelHistory: historyBookViewModel
    private lateinit var bookHistoryDao : bookHistoryDao



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        //image banner
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val dotsBanner = view.findViewById<DotsIndicator>(R.id.dotsbanner)
        val imageList = listOf(R.drawable.banner, R.drawable.banner, R.drawable.banner)
        val adapterrr = BannerAdapter(imageList)
        viewPager.adapter = adapterrr
        dotsBanner.attachTo(viewPager)


        //newest book recycleview
        val recycleViewNew = view.findViewById<RecyclerView>(R.id.Released)
        recycleViewNew.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         newestBookList = ArrayList()


         adapterr = newestBookAdapter(newestBookList,this)
        recycleViewNew.adapter = adapterr

        //viewmodel newewst book
        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        viewModel.newestBooks.observe(viewLifecycleOwner) { books ->
            adapterr.updateBookList(books)
        }
        viewModel.getNewestBooks()


       // History RecyclerView
        val recycleViewHistory = view.findViewById<RecyclerView>(R.id.recycleviewHistory)
        recycleViewHistory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        historyBookList = ArrayList()
        historyAdapter = bookHistoryAdapter(historyBookList,this)
        recycleViewHistory.adapter = historyAdapter


        val database = AppDatabase.getDatabase(requireContext())
        val bookHistoryDao = database.bookHistoryDao()
        viewModelHistory = ViewModelProvider(this, historyBookViewModelFactory(bookHistoryDao)).get(historyBookViewModel::class.java)
        val sharedPreferences = requireActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userid", null)

        if (userId!=null) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModelHistory.getAllHistorySortedByDate(userId).collect { books ->
                    historyBookList.clear()
                    historyBookList.addAll(books)
                    historyAdapter.notifyDataSetChanged()
                }
            }
        } else {
           //
        }

        return view
    }

    override fun onItemClick(book: bookHistoryEntity) {
        val intent = Intent(requireContext(), detailBook::class.java)
        intent.putExtra("book_title", book.title)
        intent.putExtra("book_image", book.imageUrl)
        intent.putExtra("book_authors", book.authors)
        intent.putExtra("book_publisher", book.publisher)
        intent.putExtra("book_publishedDate", book.publishedDate)
        intent.putExtra("book_pageCount", book.pageCount)
        intent.putExtra("book_language", book.language)
        intent.putExtra("book_categories", book.categories)
        intent.putExtra("book_description", book.description)
        intent.putExtra("book_price", book.price)
        intent.putExtra("book_saleability", book.saleability)
        intent.putExtra("buyLink", book.buyLink)



        startActivity(intent)
    }

    override fun onItemClick(book: bookModel) {
        val database = AppDatabase.getDatabase(requireContext())
        bookHistoryDao = database.bookHistoryDao()
        val factory = historyBookViewModelFactory(bookHistoryDao)
        viewModelHistory = ViewModelProvider(this, factory).get(historyBookViewModel::class.java)
        val firebaseAuth = FirebaseAuth.getInstance()
        val userid = firebaseAuth.currentUser?.uid
        if (userid != null) {
            viewModelHistory.addBookToHistory(book,userid)
        }

        val intent = Intent(requireContext(), detailBook::class.java)
        intent.putExtra("book_title", book.title)
        intent.putExtra("book_image", book.imageUrl)
        intent.putExtra("book_authors", book.authors.joinToString(", "))
        intent.putExtra("book_publisher", book.publisher)
        intent.putExtra("book_publishedDate", book.publishedDate)
        intent.putExtra("book_pageCount", book.pageCount)
        intent.putExtra("book_language", book.language)
        intent.putExtra("book_categories", book.categories.joinToString { "," })
        intent.putExtra("book_description", book.description)
        intent.putExtra("book_price", book.price)
        intent.putExtra("book_saleability", book.saleability)
        intent.putExtra("buyLink", book.buyLink)



        startActivity(intent)
    }


}