package com.example.dimplebooks.UI.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dimplebooks.R

class Preview : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preview)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
            window.statusBarColor = ContextCompat.getColor(this, R.color.navy_blue_white)

            val preview = intent.getStringExtra("book_previewLink")


            toolbar = findViewById(R.id.toolbarpreview)
            setSupportActionBar(toolbar)
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

            webView = findViewById(R.id.webViewpreview)
            webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(preview.toString())
        }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return if (item.itemId == android.R.id.home) {
                onBackPressed()
                true
            } else {
                super.onOptionsItemSelected(item)
            }
        }
    }
