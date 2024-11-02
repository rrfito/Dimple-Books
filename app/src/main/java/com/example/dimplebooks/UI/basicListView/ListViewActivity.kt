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
//        val listview : ListView = findViewById(R.id.ListView)
//        val menulist = listOf(
//            ListModel("Username",R.drawable.account),
//            ListModel("Email",R.drawable.baseline_alternate_email_24),
//            ListModel("Password",R.drawable.baseline_security_24),
//            ListModel("Log Out",R.drawable.baseline_logout_24),
//
//        )


//        val adapter = ListAdapter(this,menulist)
//        listview.adapter = adapter
//
//        listview.setOnItemClickListener{ parent,view,position,id ->
//            val selectedItem = menulist[position]
//            if(selectedItem.name == "Menu5"){
//                Toast.makeText(this, "ini adlah menu 5", Toast.LENGTH_SHORT).show()
//            }
//
//        }
    }

}