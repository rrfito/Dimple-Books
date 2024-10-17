package com.example.dimplebooks.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.model.RecycleViewBookHistory


class bookHistoryAdapter(private val HistorybookList: ArrayList<RecycleViewBookHistory>) : RecyclerView.Adapter<bookHistoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val historyBookImage: ImageView = itemView.findViewById(R.id.bookImage)
        val historyBookName: TextView = itemView.findViewById(R.id.bookName)
        val historyAuthorName: TextView = itemView.findViewById(R.id.authorInfo)
        val historyPublisher : TextView = itemView.findViewById(R.id.publisher)
        val historyGenre: TextView = itemView.findViewById(R.id.genre)
        var historyPages: TextView = itemView.findViewById(R.id.pageCount)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: bookHistoryAdapter.ViewHolder, position: Int) {
        val currentBook = HistorybookList[position]

        holder.historyBookImage.setImageResource(currentBook.image)
        holder.historyBookName.text = currentBook.nameBook
        holder.historyAuthorName.text = currentBook.author
        holder.historyPublisher.text = currentBook.publisher
        holder.historyGenre.text = currentBook.genre
        holder.historyPages.text = currentBook.page.toString()
    }

    override fun getItemCount(): Int {
        return HistorybookList.size
    }
}
