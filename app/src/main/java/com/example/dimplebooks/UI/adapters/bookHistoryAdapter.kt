package com.example.dimplebooks.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.data.entity.bookHistory


class bookHistoryAdapter(private val HistorybookList: ArrayList<bookHistory>)
    : RecyclerView.Adapter<bookHistoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val historyBookImage: ImageView = itemView.findViewById(R.id.bookImageHistory)
        val historyBookName: TextView = itemView.findViewById(R.id.bookNameHistory)
        val historyAuthorName: TextView = itemView.findViewById(R.id.authorHistory)
        val historyGenre: TextView = itemView.findViewById(R.id.genreHistory)
        var historyRead: TextView = itemView.findViewById(R.id.readHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: bookHistoryAdapter.ViewHolder, position: Int) {
        val currentBook = HistorybookList[position]
        holder.historyBookName.text = currentBook.title
        holder.historyAuthorName.text = currentBook.authors.joinToString(", ")
        holder.historyGenre.text = currentBook.categories.joinToString(", ")
        holder.historyRead.text = currentBook.openedAt.toString()
    }

    override fun getItemCount(): Int {
        return HistorybookList.size
    }
}
