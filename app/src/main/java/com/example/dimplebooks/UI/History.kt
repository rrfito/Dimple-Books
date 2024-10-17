package com.example.dimplebooks.UI

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dimplebooks.R
import com.example.dimplebooks.R.id.recycler_view_history
import com.example.dimplebooks.UI.adapters.BannerAdapter
import com.example.dimplebooks.UI.adapters.bookHistoryAdapter
import com.example.dimplebooks.model.RecycleViewBookHistory

class History : AppCompatActivity() {

    private lateinit var  RecyclerView : RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val imageList = listOf(R.drawable.banner, R.drawable.banner, R.drawable.banner)
        val adapterrr = BannerAdapter(imageList)
        viewPager.adapter = adapterrr





        val  recycleviewHistory = findViewById<RecyclerView>(recycler_view_history)
        recycleviewHistory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val historyBookList = ArrayList<RecycleViewBookHistory>()
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))
        historyBookList.add(RecycleViewBookHistory("Narnia to moon","Hans Zimmer","Nambodia","Action",200,R.drawable.book1))

        val adapter = bookHistoryAdapter(historyBookList)
        recycleviewHistory.adapter = adapter

    }
}