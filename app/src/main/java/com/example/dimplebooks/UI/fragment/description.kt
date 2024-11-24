package com.example.dimplebooks.UI.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.activity.Preview
import com.example.dimplebooks.UI.activity.buyItem
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class description : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)

        val sharedPreferences = requireActivity().getSharedPreferences("book", Context.MODE_PRIVATE)
        val title = sharedPreferences.getString("book_title", "")
        val imageUrl = sharedPreferences.getString("book_image", "")
        val authors = sharedPreferences.getString("book_authors", "")
        val publishedDate = sharedPreferences.getString("book_publishedDate", "")
        val pageCount = sharedPreferences.getString("book_pageCount", "")
        val language = sharedPreferences.getString("book_language", "")
        val categories = sharedPreferences.getString("book_categories", "")
        val description = sharedPreferences.getString("book_description", "")

        // Mengatur data ke view
        view.findViewById<TextView>(R.id.bookNameDetail).text = title
        view.findViewById<TextView>(R.id.authorDetail).text = authors
        view.findViewById<TextView>(R.id.publishedDateDetail).text = publishedDate
        view.findViewById<TextView>(R.id.pageCountDetail).text = pageCount.toString()
        view.findViewById<TextView>(R.id.LanguageDetail).text = language
        view.findViewById<TextView>(R.id.CategoriesDetail).text = categories
        view.findViewById<TextView>(R.id.descriptionDetail).text = description

        // Mengatur gambar dengan Glide
        val imageView = view.findViewById<ImageView>(R.id.bookImageDetail)

        Glide.with(this).load(imageUrl).into(imageView)




        return view
    }


}
