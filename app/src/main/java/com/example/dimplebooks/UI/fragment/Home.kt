package com.example.dimplebooks.UI.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.activity.detailBook
import com.example.dimplebooks.UI.adapters.*
import com.example.dimplebooks.UI.utils.ViewModelFactory
import com.example.dimplebooks.UI.viewModel.BookViewModel
import com.example.dimplebooks.UI.viewModel.historyBookViewModel
import com.example.dimplebooks.data.database.AppDatabase
import com.example.dimplebooks.data.database.dao.bookHistoryDao
import com.example.dimplebooks.data.database.entity.bookHistoryEntity
import com.example.dimplebooks.data.model.bookModel
import com.example.dimplebooks.data.network.RetrofitInstance
import com.example.dimplebooks.data.repository.ApiRepository
import com.example.dimplebooks.databinding.FragmentHistoryBinding
import com.example.dimplebooks.utils.baseAdapter
import com.example.dimplebooks.utils.historyBookViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class Home : Fragment(),
    baseAdapter.OnItemClickListener<bookModel>{

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookViewModel
    private lateinit var viewModelHistory: historyBookViewModel
    private lateinit var bookHistoryDao: bookHistoryDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setupViewModel()
        setupUI()
        observeViewModel()
        return binding.root
    }
    private fun setupViewModel() {
        val factory = ViewModelFactory(ApiRepository(RetrofitInstance.bookApi))
        viewModel = ViewModelProvider(requireActivity(), factory).get(BookViewModel::class.java)

        val database = AppDatabase.getDatabase(requireContext())
        bookHistoryDao = database.bookHistoryDao()
        val historyFactory = historyBookViewModelFactory(bookHistoryDao)
        viewModelHistory = ViewModelProvider(this, historyFactory).get(historyBookViewModel::class.java)
    }

    private fun setupUI() {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.greyy)
        setupBanner()
        setupRecyclerViews()
    }

    private fun setupBanner() {
        val bannerAdapter = BannerAdapter(ArrayList(), this@Home)
        binding.viewPager.adapter = bannerAdapter
        binding.dotsbanner.attachTo(binding.viewPager)

        viewModel.bannerBooks.observe(viewLifecycleOwner) { books ->
            bannerAdapter.updateBookList(books)
            if (books.isNotEmpty()) {
                bannerAdapter.startAutoSlide(binding.viewPager)
            }
        }
        viewModel.GetBannerBooks()
        binding.dotsbanner

    }

    private fun setupRecyclerViews() {
        binding.apply {
            setupRecyclerView(Released, newestBookAdapter(ArrayList(), this@Home), viewModel.newestBooks)
            setupRecyclerView(recycleviewBusiness, businessBooksAdapter(ArrayList(), this@Home), viewModel.businessBooks)
            setupRecyclerView(recycleviewEntertaiment, entertaimentBookAdapter(ArrayList(), this@Home), viewModel.entertainmentBooks)
            setupRecyclerView(recycleviewRecom, goodBooksAdapter(ArrayList(), this@Home), viewModel.recommBooks)
            setupGridRecyclerView(gridDaily, dailyBookGridAdapter(ArrayList(), this@Home), viewModel.dailyBooks)
        }
    }

    private fun  <T : baseAdapter<bookModel>> setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: T,
        liveData: androidx.lifecycle.LiveData<List<bookModel>>
    ) {
        recyclerView.layoutManager = if (recyclerView.id == R.id.recycleviewRecom) {
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        } else {
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        recyclerView.adapter = adapter
        liveData.observe(viewLifecycleOwner) { books ->
              adapter.updateBookList(books)
        }
    }

    private fun <T : RecyclerView.Adapter<*>> setupGridRecyclerView(
        recyclerView: RecyclerView,
        adapter: T,
        liveData: androidx.lifecycle.LiveData<List<bookModel>>
    ) {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter
        liveData.observe(viewLifecycleOwner) { books ->
            if (adapter is dailyBookGridAdapter) {
                adapter.updateBookList(books)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.getNewestBooks()
        viewModel.GetdailyGetBooks()
        viewModel.GetBusinessBooks()
        viewModel.GetEntertainmentBooks()
        viewModel.GetRecommendBooks()
    }

    override fun onItemClick(book: bookModel) {
        saveBookToHistory(book)
        navigateToDetail(book)
    }


    private fun saveBookToHistory(book: bookModel) {
        FirebaseAuth.getInstance().currentUser?.uid?.let { userId ->
            viewModelHistory.addBookToHistory(book, userId)
        }
    }

    private fun navigateToDetail(book: bookModel) {
        val intent = Intent(requireContext(), detailBook::class.java).apply {
            putExtra("book_title", book.title)
            putExtra("book_image", book.imageUrl)
            putExtra("book_authors", book.authors.joinToString(", "))
            putExtra("book_publisher", book.publisher)
            putExtra("book_publishedDate", book.publishedDate)
            putExtra("book_pageCount", book.pageCount.toString())
            putExtra("book_language", book.language)
            putExtra("book_categories", book.categories.joinToString(", "))
            putExtra("book_description", book.description)
            putExtra("book_price", book.price.toString())
            putExtra("book_saleability", book.saleability)
            putExtra("buyLink", book.buyLink)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
