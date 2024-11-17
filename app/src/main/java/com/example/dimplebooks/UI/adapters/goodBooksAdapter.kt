package com.example.dimplebooks.UI.adapters

import android.annotation.SuppressLint
import android.util.Log
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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dimplebooks.R
import com.example.dimplebooks.model.bookModel

class goodBooksAdapter(
    private val goodbooksList: ArrayList<bookModel>,
    private val listener: OnItemClickListener) : RecyclerView.Adapter<goodBooksAdapter.ViewHolder>() {



    interface OnItemClickListener {
        fun onItemClick(book: bookModel)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subtitleRecom = itemView.findViewById<TextView>(R.id.subtitleRecommendation)
        val image = itemView.findViewById<ImageView>(R.id.imagerecommendation)
        val bookName = itemView.findViewById<TextView>(R.id.titleRecommendation)
        val descriptionRecom = itemView.findViewById<TextView>(R.id.descriptionRecommendation)
        val page = itemView.findViewById<TextView>(R.id.pageRecommendation)
        val date = itemView.findViewById<TextView>(R.id.dateRecommendation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommendationbooks, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = goodbooksList[position]

        holder.subtitleRecom.text = currentBook.subtitle ?: "Brave Yourself"
        holder.bookName.text = if (currentBook.title.length > 20) {
            currentBook.title.take(20) + "..."
        } else {
            currentBook.title
        }
        Glide.with(holder.itemView.context)
            .load(currentBook.imageUrl)
            .fitCenter()
            .error(R.drawable.error)
            .placeholder(R.drawable.loading)
            .into(holder.image)
        holder.descriptionRecom.text = if (currentBook.description.length > 50){
                currentBook.description.take(50) + "..."

            }else{
                currentBook.description
        }
        holder.page.text = currentBook.pageCount.toString() + " pages"
        holder.date.text = currentBook.publishedDate

        holder.itemView.setOnClickListener {
            listener.onItemClick(currentBook)
        }

        val gestureDetector = GestureDetector(holder.itemView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                listener.onItemClick(currentBook)
                return true
            }
            override fun onDown(e: MotionEvent): Boolean {
                zoomIn(holder.image)
                return true
            }
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                zoomOut(holder.image)
                return super.onSingleTapUp(e)
            }
        })

        holder.image.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    zoomIn(holder.image)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    zoomOut(holder.image)
                }
            }
            true
        }





    }

    override fun getItemCount(): Int {
        return goodbooksList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateBookList(newBooks: List<bookModel>) {
        goodbooksList.clear()
        goodbooksList.addAll(newBooks)
        notifyDataSetChanged()
    }
    private fun zoomIn(view: View) {
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


