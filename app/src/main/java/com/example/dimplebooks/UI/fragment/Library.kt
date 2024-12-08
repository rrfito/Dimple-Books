package com.example.dimplebooks.UI.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.bookAdapter
import com.example.dimplebooks.UI.adapters.categoriesAdapter
import com.example.dimplebooks.UI.activity.detailBook
import com.example.dimplebooks.UI.utils.ViewModelFactory
import com.example.dimplebooks.data.database.AppDatabase
import com.example.dimplebooks.data.database.dao.bookHistoryDao
import com.example.dimplebooks.data.model.bookModel
import com.example.dimplebooks.UI.viewModel.BookViewModel
import com.example.dimplebooks.UI.viewModel.historyBookViewModel
import com.example.dimplebooks.data.network.RetrofitInstance
import com.example.dimplebooks.data.repository.ApiRepository
import com.example.dimplebooks.utils.historyBookViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Library.newInstance] factory method to
 * create an instance of this fragment.
 */
class Library : Fragment(),bookAdapter.OnItemClickListener,categoriesAdapter.OnCategoryClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonMap : Map<Int,String>

    private lateinit var bookList: ArrayList<bookModel>

    private lateinit var adapter: bookAdapter
    private lateinit var searchView: SearchView
    private lateinit var viewModel: BookViewModel
    private lateinit var viewModelHistory: historyBookViewModel
    private lateinit var bookHistoryDao: bookHistoryDao
    private val categories = listOf("Business & Economics", "cartoons",
        "Science", "Fiction","Biography","History" ,"Business & Economics")
    private lateinit var findbookText : TextView
    private lateinit var findbookImage : ImageView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val factory = ViewModelFactory(ApiRepository(RetrofitInstance.bookApi))
        viewModel = ViewModelProvider(requireActivity(),factory).get(BookViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.greyy)
        findbookText = view.findViewById(R.id.findbook)
        findbookImage  = view.findViewById(R.id.findbookimage)


         buttonMap = mapOf(
            R.id.ComicsNovel to "cartoons",
            R.id.Science to "Science",
            R.id.Fiction to "Fiction",
            R.id.Biography to "Biography",
            R.id.history to "History",
            R.id.Economics to "Business & Economics",
            R.id.Health to "Health & Fitness"
        )
        buttonMap.forEach { (buttonId, category) ->
            val button = view.findViewById<MaterialButton>(buttonId)
            button.setOnClickListener {
                // Call the function to handle category selection
                handleCategorySelection(button, category)
            }
        }

        viewModel.categoriesBooks.observe(viewLifecycleOwner) { books ->
            adapter.updateBookList(books)
        }


        // Inisialisasi RecyclerView dan Adapter
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        bookList = ArrayList()
        adapter = bookAdapter(bookList,this)
        recyclerView.adapter = adapter








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

        val firebase = FirebaseAuth.getInstance()
        val userid = firebase.currentUser?.uid
        val shared = requireActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE)
        if (userid != null) {
            viewModelHistory.addBookToHistory(book,userid)
        }
        val sharedPreferences = requireActivity().getSharedPreferences("book", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString("book_id", book.id)
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
    override fun onCategoryClick(category: String) {
        viewModel.CategoriesBooks(category)
        viewModel.setVisibility(false, false)
        findbookText.visibility = View.GONE
        findbookImage.visibility = View.GONE
    }
    private fun handleCategorySelection(button: MaterialButton, category: String) {
        // Set the selected button's appearance
        resetButtonStyles()
        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.navy_blue_white))
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        // Update the ViewModel and visibility
        viewModel.CategoriesBooks(category)
        viewModel.setVisibility(false, false)
        findbookText.visibility = View.GONE
        findbookImage.visibility = View.GONE
    }

    private fun resetButtonStyles() {
        // Reset styles for all buttons
        buttonMap.keys.forEach { buttonId ->
            val button = view?.findViewById<MaterialButton>(buttonId)
            button?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greyybutton))
            button?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }





}
