package com.example.dimplebooks.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.model.RecycleViewBook

class bookAdapter(private val bookList: ArrayList<RecycleViewBook>) : RecyclerView.Adapter<bookAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookImage: ImageView = itemView.findViewById(R.id.image)
        val bookName: TextView = itemView.findViewById(R.id.bookName)
        val authorName: TextView = itemView.findViewById(R.id.author)
        val rating: TextView = itemView.findViewById(R.id.rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: bookAdapter.ViewHolder, position: Int) {
        val currentBook = bookList[position]
        holder.bookImage.setImageResource(currentBook.image)
        holder.bookName.text = currentBook.nameBook
        holder.authorName.text = currentBook.author
        holder.rating.text = currentBook.rating.toString()
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}
