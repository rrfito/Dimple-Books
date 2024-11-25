package com.example.dimplebooks.UI.activity

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.fragment.Account
import com.example.dimplebooks.UI.fragment.Library
import com.example.dimplebooks.UI.fragment.History
import com.example.dimplebooks.data.NetworkChangeReceiver
import com.example.dimplebooks.UI.viewModel.BookViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class MainNavigasi : AppCompatActivity() {
    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    private var isConnected = false

    override fun onStart() {
        super.onStart()
        networkChangeReceiver = NetworkChangeReceiver { isConnected ->
            this.isConnected = isConnected
            if (!isConnected) {
                Fail()

            }
        }
        val intentFilter = android.content.IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        // Unregister the receiver to prevent memory leaks
        unregisterReceiver(networkChangeReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigasi)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }


        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)


        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, History())
            .commit()

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
                R.id.accountButton -> {
                    loadHFragment(Account())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadHFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    private fun Fail() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_notforsale_item)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val message = dialog.findViewById<TextView>(R.id.textDialog)
        message.setText("Oops, it looks like your internet connection is down.")

        val button = dialog.findViewById<MaterialButton>(R.id.btnNotForSaleYes)
        button.setText("Refresh")

        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        button.setOnClickListener {
            if (isConnected) {
                val viewModel = ViewModelProvider(this).get(BookViewModel::class.java)
                viewModel.refreshAPI()
                viewModel.getNewestBooks()
                viewModel.GetBannerBooks()
                viewModel.GetdailyGetBooks()
                viewModel.GetBusinessBooks()
                viewModel.GetEntertainmentBooks()
                viewModel.GetRecommendBooks()
                Toast.makeText(this, "Successfully connected to the internet", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "No internet connection. Please try again later.", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}
