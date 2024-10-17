package com.example.dimplebooks.UI

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.bookAdapter
import com.example.dimplebooks.model.RecycleViewBook

class Search : AppCompatActivity() {

    private lateinit var RecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


       val  recycleview = findViewById<RecyclerView>(R.id.recycler_view)

        recycleview.layoutManager = GridLayoutManager(this,2)
        val booklist = ArrayList<RecycleViewBook>()
        booklist.add(RecycleViewBook("The Joker felix","Suzan Hasanna", 5.5,R.drawable.book1))
        booklist.add(RecycleViewBook("The Joker felix","Suzan Hasanna", 5.5,R.drawable.book2))
        booklist.add(RecycleViewBook("The Joker felix","Suzan Hasanna", 5.5,R.drawable.book1))
        booklist.add(RecycleViewBook("The Joker felix","Suzan Hasanna", 5.5,R.drawable.book2))
        booklist.add(RecycleViewBook("The Joker felix","Suzan Hasanna", 5.5,R.drawable.book1))
        booklist.add(RecycleViewBook("The Joker felix","Suzan Hasanna", 5.5,R.drawable.book2))
        booklist.add(RecycleViewBook("The Joker felix","Suzan Hasanna", 5.5,R.drawable.book1))
        booklist.add(RecycleViewBook("The Joker felix","Suzan Hasanna", 5.5,R.drawable.book2))


        val adapterr = bookAdapter(booklist)
        recycleview.adapter = adapterr




    }
}