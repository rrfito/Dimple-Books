package com.example.dimplebooks.UI.adapters

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
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
        val index : TextView = itemView.findViewById(R.id.urutanHistory)
    }
    interface OnItemClickListener {
        fun onItemClick(book: bookHistoryEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_history, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: bookHistoryAdapter.ViewHolder, position: Int) {
        val currentBook = HistorybookList[position]
        holder.historyBookName.text = if (currentBook.title.length > 20) {
            currentBook.title.take(20)+"..."
        }else{
            currentBook.title
        }
        holder.index.text = (position+1).toString()
        holder.historyAuthorName.text = currentBook.authors
        holder.historyGenre.text = currentBook.categories
        holder.historyRead.text = formatTimestamp(currentBook.timestamp)
        Glide.with(holder.itemView.context)
            .load(currentBook.imageUrl)
            .into(holder.historyBookImage)

        holder.itemView.setOnClickListener {
            listener.onItemClick(currentBook)
        }

        val gestureDetector = GestureDetector(holder.itemView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                listener.onItemClick(currentBook)
                return true
            }
            override fun onDown(e: MotionEvent): Boolean {
                zoomIn(holder.historyBookImage)
                return true
            }
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                zoomOut(holder.historyBookImage)
                return super.onSingleTapUp(e)
            }
        })

        holder.historyBookImage.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    zoomIn(holder.historyBookImage)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    zoomOut(holder.historyBookImage)
                }
            }
            true
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
    }private fun zoomIn(view: View) {
        val zoomIn = ScaleAnimation(
            1.0f, 1.5f,
            1.0f, 1.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        zoomIn.fillAfter = true
        zoomIn.duration = 300
        view.startAnimation(zoomIn)
    }

    private fun zoomOut(view: View) {
        val zoomOut = ScaleAnimation(
            1.5f, 1.0f,
            1.5f, 1.0f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        zoomOut.fillAfter = true
        zoomOut.duration = 300
        view.startAnimation(zoomOut)
    }
}
