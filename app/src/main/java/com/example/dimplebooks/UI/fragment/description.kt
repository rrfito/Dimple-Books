package com.example.dimplebooks.UI.fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.dimplebooks.UI.activity.RatingBook

import com.example.dimplebooks.databinding.FragmentDescriptionBinding

class description : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)


        val sharedPreferences = requireActivity().getSharedPreferences("book", Context.MODE_PRIVATE)
        val title = sharedPreferences.getString("book_title", "N/A")
        val imageUrl = sharedPreferences.getString("book_image", null)
        val authors = sharedPreferences.getString("book_authors", "Unknown Author")
        val publishedDate = sharedPreferences.getString("book_publishedDate", "N/A")
        val pageCount = sharedPreferences.getString("book_pageCount", "N/A")
        val language = sharedPreferences.getString("book_language", "Unknown Language")
        val categories = sharedPreferences.getString("book_categories", "Uncategorized")
        val description = sharedPreferences.getString("book_description", "No description available.")

        binding.apply {
            bookNameDetail.text = title
            authorDetail.text = authors
            publishedDateDetail.text = publishedDate
            pageCountDetail.text = pageCount
            LanguageDetail.text = language
            CategoriesDetail.text = categories
            descriptionDetail.text = description
            Glide.with(this@description).load(imageUrl).into(bookImageDetail)
            }
        binding.rateButton.setOnClickListener{
            val i = Intent(requireContext(), RatingBook::class.java)
            startActivity(i)

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
