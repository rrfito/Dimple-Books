package com.example.dimplebooks.UI

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.fragment.Account
import com.example.dimplebooks.UI.fragment.Library
import com.example.dimplebooks.UI.fragment.History
import com.example.dimplebooks.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainNavigasi : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main_navigasi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
        window.statusBarColor = ContextCompat.getColor(this,R.color.navy_blue_white)




        // Set the initial fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, History())
            .commit()

        // Handle bottom navigation item selection
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.historyButton -> {
                    loadHFragment(History())
                    true
                }
                R.id.libraryButton -> {
                    loadHFragment(Library())
                    true
                }
                R.id.accountButton ->{
                    loadHFragment(Account())
                    true
                }
                else -> false
            }
        }
    }


    private fun loadHFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }



}


