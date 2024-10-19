package com.example.dimplebooks.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.example.dimplebooks.model.BookResponse
import com.example.dimplebooks.model.RecycleViewBook

class bookAdapter(private val bookList: ArrayList<BookResponse>, private val itemClick: (BookResponse) -> Unit) :
    RecyclerView.Adapter<bookAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookImage: ImageView = itemView.findViewById(R.id.imagee)
        val bookName: TextView = itemView.findViewById(R.id.bookName)
        val authorName: TextView = itemView.findViewById(R.id.author)
        val rating: TextView = itemView.findViewById(R.id.rating)


        fun bind(book: BookResponse, clickListener: (BookResponse) -> Unit) {
            itemView.setOnClickListener { clickListener(book) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBookResponse = bookList[position]
        // Assuming currentBookResponse.items is a List<Book>
        val currentBook = currentBookResponse.items.firstOrNull() // You can handle multiple books in this way or choose a specific one
        // Load the image using Glide if imageLinks are available
        val imageUrl = currentBook?.volumeInfo?.imageLinks?.thumbnail ?: "" // Default to empty if not available
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.bookImage)
        // Set book details
        holder.bookName.text = currentBook?.volumeInfo?.title ?: "Unknown Title"
        holder.authorName.text = currentBook?.volumeInfo?.authors?.joinToString(", ") ?: "Unknown Author"
        holder.rating.text = currentBook?.price?.amount?.toString() ?: "N/A"

        // Bind click listener
        holder.bind(currentBookResponse) { itemClick(currentBookResponse) }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
    fun setBooks(books: List<BookResponse>) {
        // Update the bookList with new data
        this.bookList.clear() // Clear the old data
        this.bookList.addAll(books) // Add new data
        notifyDataSetChanged() // Notify RecyclerView that data has changed
    }
}
