package com.example.dimplebooks.UI.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.BannerAdapter
import com.example.dimplebooks.UI.adapters.bookHistoryAdapter
import com.example.dimplebooks.UI.adapters.newestBookAdapter
import com.example.dimplebooks.UI.activity.detailBook
import com.example.dimplebooks.UI.adapters.businessBooksAdapter
import com.example.dimplebooks.UI.adapters.dailyBookGridAdapter
import com.example.dimplebooks.UI.adapters.entertaimentBookAdapter
import com.example.dimplebooks.UI.adapters.goodBooksAdapter
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
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
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
class History : Fragment(),bookHistoryAdapter.OnItemClickListener,newestBookAdapter.OnItemClickListener,businessBooksAdapter.OnItemClickListener,entertaimentBookAdapter.OnItemClickListener,BannerAdapter.OnItemClickListener,goodBooksAdapter.OnItemClickListener,dailyBookGridAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var newestBookList: ArrayList<bookModel>
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

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.greyy)
        //image banner
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val dotsBanner = view.findViewById<SpringDotsIndicator>(R.id.dotsbanner)
        val bannerList = ArrayList<bookModel>()
        val adapterrr = BannerAdapter(bannerList,this)
        viewPager.adapter = adapterrr
        dotsBanner.attachTo(viewPager)
        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        viewModel.getNewestBooks()
        viewModel.bannerBooks.observe(viewLifecycleOwner) { books ->
            adapterrr.updateBookList(books)
            if (books.isNotEmpty()) {
                adapterrr.startAutoSlide(viewPager)
            }
        }
        viewModel.GetBannerBooks()
        Log.d("test banner list","adalh : ${bannerList.size}")
        //newest book recycleview
        val recycleViewNew = view.findViewById<RecyclerView>(R.id.Released)
        recycleViewNew.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         newestBookList = ArrayList()
        val  adapterr = newestBookAdapter(newestBookList,this)
        recycleViewNew.adapter = adapterr
        //viewmodel newewst book
        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        viewModel.newestBooks.observe(viewLifecycleOwner) { books ->
            adapterr.updateBookList(books)
        }
        viewModel.getNewestBooks()

        //dailypics grid
        val recycleGrid = view.findViewById<RecyclerView>(R.id.gridDaily)
        val GridList = ArrayList<bookModel>()
        val adapterGrid = dailyBookGridAdapter(GridList,this)
        recycleGrid.layoutManager= GridLayoutManager(requireContext(),3)
        recycleGrid.adapter = adapterGrid
        //viewmodel dailypics
        viewModel.dailyBooks.observe(viewLifecycleOwner){books ->
            adapterGrid.updateBookList(books)
        }
        viewModel.GetdailyGetBooks()
        //recycle view business
        val recycleViewBusiness = view.findViewById<RecyclerView>(R.id.recycleviewBusiness)
        recycleViewBusiness.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val businessBookList = ArrayList<bookModel>()
        val adapter = businessBooksAdapter(businessBookList,this)
        recycleViewBusiness.adapter = adapter
        //viewmodel business book
        viewModel.businessBooks.observe(viewLifecycleOwner){books ->
           adapter.updateBookList(books)
        }
        viewModel.GetBusinessBooks()

        //recycle view entertaiment
        val recyclerViewEntertaiment = view.findViewById<RecyclerView>(R.id.recycleviewEntertaiment)
        recyclerViewEntertaiment.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val entertaimentBookList = ArrayList<bookModel>()
        val adapterEntertaiment = entertaimentBookAdapter(entertaimentBookList,this)
        recyclerViewEntertaiment.adapter = adapterEntertaiment
        //viewmodel entertaiment book
        viewModel.entertainmentBooks.observe(viewLifecycleOwner){books ->
            adapterEntertaiment.updateBookList(books)
        }
        viewModel.GetEntertainmentBooks()

        //recycle view recomm
        val recycleViewRecom = view.findViewById<RecyclerView>(R.id.recycleviewRecom)
        val recomBookList = ArrayList<bookModel>()
        val recomAdapter = goodBooksAdapter(recomBookList,this)
        recycleViewRecom.adapter = recomAdapter
        recycleViewRecom.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewModel.recommBooks.observe(viewLifecycleOwner){ books ->
            recomAdapter.updateBookList(books)
        }
        viewModel.GetRecommendBooks()
        Log.d("liat jumlah","jumlah : ${recomBookList.size}")










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

        val sharedPreferences = requireActivity().getSharedPreferences("book", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString("book_title", book.title)
            .putString("book_image", book.imageUrl)
            .putString("book_authors", book.authors.joinToString(", "))
            .putString("book_publisher", book.publisher)
            .putString("book_publishedDate", book.publishedDate)
            .putString("book_pageCount", book.pageCount.toString())
            .putString("book_language", book.language)
            .putString("book_categories", book.categories.joinToString(", "))
            .putString("book_description", book.description)
            .putString("book_price", book.price.toString())
            .putString("book_saleability", book.saleability)
            .putString("buyLink", book.buyLink)
            .putString("book_previewLink", book.previewLink)
            .putFloat("book_rating", book.rating.toFloat())
            .apply()

        val intent = Intent(requireContext(), detailBook::class.java)
        Log.d("IntentData", " Title: ${book.title}, price : ${book.price}, buylink : ${book.buyLink}")

        startActivity(intent)
    }


}