package com.example.dimplebooks.UI.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class detailBook : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_book)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = ContextCompat.getColor(this,R.color.white)

        val title = intent.getStringExtra("book_title")
        val imageUrl = intent.getStringExtra("book_image")
        val authors = intent.getStringExtra("book_authors")


        val publishedDate = intent.getStringExtra("book_publishedDate")
        val pageCount = intent.getIntExtra("book_pageCount", 0)
        val language = intent.getStringExtra("book_language")
        val categories = intent.getStringExtra("book_categories")
        val description = intent.getStringExtra("book_description")
        val buyLink = intent.getStringExtra("buyLink")
        val price = intent.getIntExtra("book_price",0)
        val background = intent.getStringExtra("book_image")

        Log.d("IntentDataa", " Title: ${title}, Authors: ${authors}, price : ${price}, buyLink : ${buyLink}")









        findViewById<TextView>(R.id.bookNameDetail).text = title
        findViewById<TextView>(R.id.authorDetail).text = authors
        findViewById<TextView>(R.id.publishedDateDetail).text = publishedDate
        findViewById<TextView>(R.id.pageCountDetail).text = pageCount.toString()
        findViewById<TextView>(R.id.LanguageDetail).text = language
        findViewById<TextView>(R.id.CategoriesDetail).text = categories
        findViewById<TextView>(R.id.descriptionDetail).text = description
        findViewById<ImageView>(R.id.backFromDetail).setOnClickListener {
            finish()
        }

        val imageView = findViewById<ImageView>(R.id.bookImageDetail)
        val backgroundView = findViewById<ImageView>(R.id.backgrounddetail)
        Glide.with(this).load(imageUrl).into(imageView)
        Glide.with(this).load(background).into(backgroundView)

        val buy = findViewById<ImageView>(R.id.buy)

        buy.setOnClickListener {
            if (price != 0 && buyLink != null) {
                showForSaleDialog(price, buyLink)

            } else {
                showNotForSaleDialog()
            }
        }


    } private fun showForSaleDialog(price: Int, buyLink: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_forsale_item)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val priceTextView = dialog.findViewById<TextView>(R.id.priceForsale)
        priceTextView.text = "Price : Rp $price"

        dialog.findViewById<MaterialButton>(R.id.btnYesForSale).setOnClickListener {
            val intent = Intent(this, buyItem::class.java)
            intent.putExtra("buylinkk", buyLink)
            startActivity(intent)
            dialog.dismiss()
        }
        dialog.findViewById<MaterialButton>(R.id.btnNoForSale).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showNotForSaleDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_notforsale_item)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.findViewById<MaterialButton>(R.id.btnNotForSaleYes).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}