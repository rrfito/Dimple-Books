package com.example.dimplebooks.UI.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.bookAdapter
import com.example.dimplebooks.UI.adapters.bookHistoryAdapter
import com.example.dimplebooks.UI.detailBook
import com.example.dimplebooks.model.BookResponse
import com.example.dimplebooks.model.RecycleViewBook
import com.example.dimplebooks.retrofit.libraryService
import retrofit2.Response
import retrofit2.Callback
import retrofit2.Call

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

    private lateinit var adapter: bookAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_library, container, false)

         recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
         searchView = view.findViewById<SearchView>(R.id.search)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

      //  adapter = bookAdapter(arrayListOf())
        recyclerView.adapter = adapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    loadBooks(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optional: handle real-time search
                return false
            }
        })
        return view
    }

    private fun loadBooks(query: String) {
        libraryService
            .api
            .getBooks(query,apiKey = "AIzaSyDKJRBAPtyxNKAW2lJx-LY6169BlIg_lqU")
            .enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items ?: emptyList()
                     //adapter.setBooks(books)
                }
            }

                override fun onFailure(call: Call<BookResponse>, t: Throwable) {

                }

                //override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                // Handle API error
            //}
        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Library.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Library().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}