package com.example.dimplebooks.UI.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.bookAdapter
import com.example.dimplebooks.model.bookModel
import com.example.dimplebooks.model.BookResponse
import com.example.dimplebooks.retrofit.bookApi
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Library.newInstance] factory method to
 * create an instance of this fragment.
 */
class Library : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookList: ArrayList<bookModel>
    private lateinit var adapter: bookAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)

        // Inisialisasi RecyclerView dan Adapter
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        bookList = ArrayList()
        adapter = bookAdapter(bookList)
        recyclerView.adapter = adapter


        searchView = view.findViewById(R.id.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchBookss(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })


        return view
    }

    private fun searchBookss(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val bookApi = retrofit.create(bookApi::class.java)
        val call = bookApi.searchBooks(query, "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU",maxResults = 35)

        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val bookResponse = response.body()
                    bookResponse?.items?.let {
                        val books = it.map { item ->
                            bookModel(
                                title = item.volumeInfo.title,
                                authors = item.volumeInfo.authors ?: listOf("Unknown Author"),
                                publisher = item.volumeInfo.publisher ?: "Unknown Publisher",
                                price = item.saleInfo.listPrice?.amount?.toInt(),

                                imageUrl = (item.volumeInfo.imageLinks?.thumbnail ?: "").replace("http:", "https:")
                                    .replace("&edge=curl", "")
                                    .replace("zoom=1", "zoom=0"),

                                description = item.volumeInfo.description ?: "No description",
                                categories = item.volumeInfo.categories ?: listOf("Unknown"),
                                saleability = item.saleInfo.saleability
                            )
                        }

                        updateBookList(books)
                    }
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("Library", "Error fetching books", t)
            }
        })
    }

    private fun updateBookList(books: List<bookModel>) {
        bookList.clear()
        bookList.addAll(books)
        adapter.notifyDataSetChanged()
    }
}
