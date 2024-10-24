package com.example.dimplebooks.UI.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.bookAdapter
import com.example.dimplebooks.UI.detailBook
import com.example.dimplebooks.data.AppDatabase
import com.example.dimplebooks.data.dao.bookHistoryDao
import com.example.dimplebooks.model.bookModel
import com.example.dimplebooks.viewModel.BookViewModel
import com.example.dimplebooks.viewModel.historyBookViewModel
import com.example.dimplebooks.viewModel.historyBookViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Library.newInstance] factory method to
 * create an instance of this fragment.
 */
class Library : Fragment(),bookAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookList: ArrayList<bookModel>
    private lateinit var adapter: bookAdapter
    private lateinit var searchView: SearchView
    private lateinit var viewModel: BookViewModel
    private lateinit var viewModelHistory: historyBookViewModel
    private lateinit var bookHistoryDao: bookHistoryDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        val findbookText : TextView = view.findViewById(R.id.findbook)
        val findbookImage : ImageView = view.findViewById(R.id.findbookimage)

        // Inisialisasi RecyclerView dan Adapter
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        bookList = ArrayList()
        adapter = bookAdapter(bookList,this)
        recyclerView.adapter = adapter


        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        // Observasi data dari ViewModel
        viewModel.searchBooks.observe(viewLifecycleOwner) { books ->
            adapter.updateBookList(books)
        }

        findbookImage.visibility = if (viewModel.isImageVisible) View.VISIBLE else View.GONE
        findbookText.visibility = if (viewModel.isTextVisible) View.VISIBLE else View.GONE
        //search
        searchView = view.findViewById(R.id.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.setVisibility(false, false)
                    findbookText.visibility = View.GONE
                    findbookImage.visibility = View.GONE
                    viewModel.searchBooks(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })



        return view
    }
    override fun onItemClick(book: bookModel) {
        val database = AppDatabase.getDatabase(requireContext())
        bookHistoryDao = database.bookHistoryDao()
        val factory = historyBookViewModelFactory(bookHistoryDao)
        viewModelHistory = ViewModelProvider(this, factory).get(historyBookViewModel::class.java)

        viewModelHistory.addBookToHistory(book)



        val intent = Intent(requireContext(), detailBook::class.java)
        intent.putExtra("book_title", book.title)
        intent.putExtra("book_image", book.imageUrl)
        intent.putExtra("book_authors", book.authors.joinToString(", "))
        intent.putExtra("book_publisher", book.publisher)
        intent.putExtra("book_publishedDate", book.publishedDate)
        intent.putExtra("book_pageCount", book.pageCount)
        intent.putExtra("book_language", book.language)
        intent.putExtra("book_categories", book.categories.joinToString(", "))
        intent.putExtra("book_description", book.description)
        intent.putExtra("book_price", book.price)
        intent.putExtra("book_saleability", book.saleability)
        intent.putExtra("buyLink", book.buyLink)
        Log.d("IntentData", " Title: ${book.title}, price : ${book.price}, buylink : ${book.buyLink}")



        startActivity(intent)
    }




}
