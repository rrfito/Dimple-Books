package com.example.dimplebooks.UI.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.bookHistoryAdapter
import com.example.dimplebooks.data.database.AppDatabase
import com.example.dimplebooks.data.database.entity.bookHistoryEntity
import com.example.dimplebooks.UI.viewModel.historyBookViewModel
import com.example.dimplebooks.utils.historyBookViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class HistoryBooks : AppCompatActivity(), bookHistoryAdapter.OnItemClickListener {
    private lateinit var viewModelHistory: historyBookViewModel
    private lateinit var historyBookList: ArrayList<bookHistoryEntity>
    private lateinit var historyAdapter: bookHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history_books)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val database = AppDatabase.getDatabase(this)
        val bookHistoryDao = database.bookHistoryDao()
        viewModelHistory =
            ViewModelProvider(this, historyBookViewModelFactory(bookHistoryDao))
                .get(historyBookViewModel::class.java)

        val recycleViewHistory = findViewById<RecyclerView>(R.id.recycleviewHistory)
        recycleViewHistory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        historyBookList = ArrayList()
        historyAdapter = bookHistoryAdapter(historyBookList, this)
        recycleViewHistory.adapter = historyAdapter

        val userId = FirebaseAuth.getInstance().currentUser?.uid


        lifecycleScope.launch {
            if (userId != null) {
                viewModelHistory.getAllHistorySortedByDate(userId).collect { books ->
                    historyBookList.clear()
                    historyBookList.addAll(books)
                    historyAdapter.notifyDataSetChanged()
                }
            }
        }
    }
    override fun onItemClick(book: bookHistoryEntity) {
        val intent = Intent(this, detailBook::class.java)
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
}
