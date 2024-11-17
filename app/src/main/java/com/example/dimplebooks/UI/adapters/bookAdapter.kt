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
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dimplebooks.R
import com.example.dimplebooks.model.bookModel

class bookAdapter(
    private val bookList: ArrayList<bookModel>,
    private val listener: OnItemClickListener) : RecyclerView.Adapter<bookAdapter.ViewHolder>() {



    interface OnItemClickListener {
        fun onItemClick(book: bookModel)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookImage: ImageView = itemView.findViewById(R.id.image)
        val bookName: TextView = itemView.findViewById(R.id.bookName)
        val authorName: TextView = itemView.findViewById(R.id.author)
        val price: TextView = itemView.findViewById(R.id.price)
        val card : CardView = itemView.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = bookList[position]
        holder.bookName.text = if (currentBook.title.length > 10) {
            currentBook.title.take(15) + "..."
        } else {
            currentBook.title
        }
        holder.authorName.text = if(currentBook.authors.joinToString(",").length > 10){
            currentBook.authors.joinToString(",").take(15) + "..."
        }else{
            currentBook.authors.joinToString(",")
        }
        val priceText = when (currentBook.saleability) {
            "FOR_SALE" -> "Rp ${currentBook.price}"
            else -> "NOT FOR SALE"
        }
        if(priceText == "NOT FOR SALE"){
            holder.price.setTextColor(holder.itemView.context.getColor(R.color.red))
        }else{
            holder.price.setTextColor(holder.itemView.context.getColor(R.color.orange_white))
        }
        holder.price.text = priceText


        Log.d("BookAdapter", "Image URL: ${currentBook.imageUrl}")
        Glide.with(holder.itemView.context)
            .load(currentBook.imageUrl)
            .fitCenter()
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.bookImage)

        holder.itemView.setOnClickListener {
            listener.onItemClick(currentBook)
        }

        val gestureDetector = GestureDetector(holder.itemView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                listener.onItemClick(currentBook)
                return true
            }
            override fun onDown(e: MotionEvent): Boolean {
                zoomIn(holder.card)
                return true
            }
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                zoomOut(holder.card)
                return super.onSingleTapUp(e)
            }
        })

        holder.card.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    zoomIn(holder.card)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    zoomOut(holder.card)
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
            1.0f, 1.08f,
            1.0f, 1.08f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        zoomIn.fillAfter = true
        zoomIn.duration = 300
        view.startAnimation(zoomIn)
    }

    private fun zoomOut(view: View) {
        val zoomOut = ScaleAnimation(
            1.08f, 1.0f,
            1.08f, 1.0f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        zoomOut.fillAfter = true
        zoomOut.duration = 300
        view.startAnimation(zoomOut)
    }
}


