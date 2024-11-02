package com.example.dimplebooks.UI.adapters

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.bookAdapter.OnItemClickListener
import com.example.dimplebooks.data.entity.bookHistoryEntity


class bookHistoryAdapter(private val HistorybookList: ArrayList<bookHistoryEntity>,
    private val listener: OnItemClickListener)
    : RecyclerView.Adapter<bookHistoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val historyBookImage: ImageView = itemView.findViewById(R.id.bookImageHistory)
        val historyBookName: TextView = itemView.findViewById(R.id.bookNameHistory)
        val historyAuthorName: TextView = itemView.findViewById(R.id.authorHistory)
        val historyGenre: TextView = itemView.findViewById(R.id.genreHistory)
        var historyRead: TextView = itemView.findViewById(R.id.readHistory)
    }
    interface OnItemClickListener {
        fun onItemClick(book: bookHistoryEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: bookHistoryAdapter.ViewHolder, position: Int) {
        val currentBook = HistorybookList[position]
        holder.historyBookName.text = currentBook.title
        holder.historyAuthorName.text = currentBook.authors
        holder.historyGenre.text = currentBook.categories
        holder.historyRead.text = formatTimestamp(currentBook.timestamp)
        Glide.with(holder.itemView.context)
            .load(currentBook.imageUrl)
            .into(holder.historyBookImage)

        holder.itemView.setOnClickListener {
            listener.onItemClick(currentBook)
        }
    }

    override fun getItemCount(): Int {
        return HistorybookList.size
    }
    private fun formatTimestamp(timestamp: Long): String {
        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - timestamp

        return when {
            timeDifference < DateUtils.MINUTE_IN_MILLIS -> "baru saja"
            timeDifference < DateUtils.HOUR_IN_MILLIS -> {
                val minutes = timeDifference / DateUtils.MINUTE_IN_MILLIS
                "$minutes menit yang lalu"
            }
            timeDifference < DateUtils.DAY_IN_MILLIS -> {
                val hours = timeDifference / DateUtils.HOUR_IN_MILLIS
                "$hours jam yang lalu"
            }
            timeDifference < DateUtils.WEEK_IN_MILLIS -> {
                val days = timeDifference / DateUtils.DAY_IN_MILLIS
                if (days == 1L) "kemarin" else "$days hari yang lalu"
            }
            timeDifference < DateUtils.YEAR_IN_MILLIS -> {
                val months = timeDifference / DateUtils.DAY_IN_MILLIS / 30
                if (months < 1) {
                    val weeks = timeDifference / DateUtils.WEEK_IN_MILLIS
                    "$weeks minggu yang lalu"
                } else {
                    "$months bulan yang lalu"
                }
            }
            else -> {
                val years = timeDifference / DateUtils.YEAR_IN_MILLIS
                "$years tahun yang lalu"
            }
        }
    }
}
