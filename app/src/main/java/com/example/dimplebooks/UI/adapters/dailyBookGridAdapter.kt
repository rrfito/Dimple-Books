package com.example.dimplebooks.UI.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dimplebooks.R
import com.example.dimplebooks.model.bookModel

class dailyBookGridAdapter(
    private val bookList: ArrayList<bookModel>,
    private val listener: OnItemClickListener) : RecyclerView.Adapter<dailyBookGridAdapter.ViewHolder>() {



    interface OnItemClickListener {
        fun onItemClick(book: bookModel)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.dailypics1)
        val textView = itemView.findViewById<TextView>(R.id.textDailypics1)
        val card = itemView.findViewById<CardView>(R.id.cardbuynowDaily)
        val frame = itemView.findViewById<FrameLayout>(R.id.framelayoutzoom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_pics_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = bookList[position]
        holder.textView.text = if (currentBook.title.length > 20) {
            currentBook.title.take(20) + "..."
        } else {
            currentBook.title
        }
        val seasibility = currentBook.saleability
        if(seasibility == "FOR_SALE"){
            holder.card.visibility = View.VISIBLE
        }else{
            holder.card.visibility = View.GONE
        }

        Glide.with(holder.itemView.context)
            .load(currentBook.imageUrl)
            .fitCenter()
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            listener.onItemClick(currentBook)
        }

        val gestureDetector = GestureDetector(holder.itemView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                listener.onItemClick(currentBook)
                return true
            }
            override fun onDown(e: MotionEvent): Boolean {
                zoomIn(holder.frame)
                return true
            }
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                zoomOut(holder.frame)
                return super.onSingleTapUp(e)
            }
        })

        holder.frame.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    zoomIn(holder.frame)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    zoomOut(holder.frame)
                }
            }
            true
        }





    }

    override fun getItemCount(): Int {
        return bookList.size
    }
    fun updateBookList(newBooks: List<bookModel>) {
        bookList.clear()
        bookList.addAll(newBooks)
        notifyDataSetChanged()
    }
    private fun zoomIn(view: View) {
        val zoomIn = ScaleAnimation(
            1.0f, 1.1f,
            1.0f, 1.1f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        zoomIn.fillAfter = true
        zoomIn.duration = 300
        view.startAnimation(zoomIn)
    }

    private fun zoomOut(view: View) {
        val zoomOut = ScaleAnimation(
            1.1f, 1.0f,
            1.1f, 1.0f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        zoomOut.fillAfter = true
        zoomOut.duration = 300
        view.startAnimation(zoomOut)
    }
}


