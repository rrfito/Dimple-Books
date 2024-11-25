package com.example.dimplebooks.UI.adapters

import android.annotation.SuppressLint
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
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.fragment.History

import com.example.dimplebooks.data.model.bookModel

class businessBooksAdapter(private val businessBookList: ArrayList<bookModel>,
                           private val itemclick: OnItemClickListener
)
    : RecyclerView.Adapter<businessBooksAdapter.ViewHolder>() {

    interface  OnItemClickListener{
        fun onItemClick(book: bookModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val businessImage = itemView.findViewById<ImageView>(R.id.businessimages)
        val businessTittle = itemView.findViewById<TextView>(R.id.businessTittle)
        val cardbusiness = itemView.findViewById<CardView>(R.id.cardBusiness)
        val price = itemView.findViewById<TextView>(R.id.price)
        val rating = itemView.findViewById<TextView>(R.id.rating)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): businessBooksAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.business_books, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = businessBookList[position]
        Glide.with(holder.itemView.context)
            .load(currentBook.imageUrl)
            .fitCenter()
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.businessImage)
        holder.businessTittle.text = if(currentBook.title.length > 30){
            currentBook.title.take(30) + "..."
        }else{
            currentBook.title
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
        holder.rating.text = currentBook.rating.toString()
        holder.itemView.setOnClickListener {
            itemclick.onItemClick(currentBook)
        }

        val gestureDetector = GestureDetector(holder.itemView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                itemclick.onItemClick(currentBook)
                return true
            }
            override fun onDown(e: MotionEvent): Boolean {
                zoomIn(holder.cardbusiness)
                return true
            }
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                zoomOut(holder.cardbusiness)
                return super.onSingleTapUp(e)
            }
        })

        holder.cardbusiness.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    zoomIn(holder.cardbusiness)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    zoomOut(holder.cardbusiness)
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return businessBookList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateBookList(newBookList: List<bookModel>) {
        businessBookList.clear()
        businessBookList.addAll(newBookList)
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