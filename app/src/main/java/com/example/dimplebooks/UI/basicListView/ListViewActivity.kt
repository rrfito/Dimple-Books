package com.example.dimplebooks.UI.basicListView

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.dimplebooks.R

class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listview : ListView = findViewById(R.id.ListView)
        val menulist = listOf(
            ListModel("menu1","deskripsi item 1",R.drawable.history),
            ListModel("menu2","deskripsi item 2",R.drawable.account),
            ListModel("menu3","deskripsi item 3",R.drawable.library),
            ListModel("menu4","deskripsi item 4",R.drawable.icon_library),
            ListModel("menu5","deskripsi item 5",R.drawable.book1),
            ListModel("menu6","deskripsi item 6",R.drawable.book2),
        )


        val adapter = ListAdapter(this,menulist)
        listview.adapter = adapter

        listview.setOnItemClickListener{ parent,view,position,id ->
            val selectedItem = menulist[position]
            if(selectedItem.name == "Menu5"){
                Toast.makeText(this, "ini adlah menu 5", Toast.LENGTH_SHORT).show()
            }

        }
    }

}