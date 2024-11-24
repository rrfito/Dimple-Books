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
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.fragment.History

import com.example.dimplebooks.model.bookModel

class entertaimentBookAdapter(private val EntertaimentBookList: ArrayList<bookModel>,
                           private val itemclick: OnItemClickListener
)
    : RecyclerView.Adapter<entertaimentBookAdapter.ViewHolder>() {

    interface  OnItemClickListener{
        fun onItemClick(book: bookModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val businessImage = itemView.findViewById<ImageView>(R.id.businessimages)
        val businesstittle = itemView.findViewById<TextView>(R.id.businessTittle)
        val price = itemView.findViewById<TextView>(R.id.price)
        val rating = itemView.findViewById<TextView>(R.id.rating)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): entertaimentBookAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.business_books, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = EntertaimentBookList[position]
        Glide.with(holder.itemView.context)
            .load(currentBook.imageUrl)
            .fitCenter()
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.businessImage)
        holder.businesstittle.text = if(currentBook.title.length > 30){
            currentBook.title.take(30) + "..."
        }else{
            currentBook.title
        }
        val pricetext = when(currentBook.saleability){
            "FOR_SALE" -> "Rp ${currentBook.price}"
            else -> "NOT FOR SALE"
        }
        if(pricetext == "NOT FOR SALE"){
            holder.price.setTextColor(holder.itemView.context.getColor(R.color.red))
        }else{
            holder.price.setTextColor(holder.itemView.context.getColor(R.color.orange_white))
        }
        holder.price.text = pricetext
        holder.rating.text = currentBook.rating.toString()
        Log.d("wwwwwwwwwwwww","rating : ${currentBook.rating}")
        holder.itemView.setOnClickListener {
            itemclick.onItemClick(currentBook)
        }

        holder.itemView.setOnClickListener {
            itemclick.onItemClick(currentBook)
        }

        val gestureDetector = GestureDetector(holder.itemView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                itemclick.onItemClick(currentBook)
                return true
            }
            override fun onDown(e: MotionEvent): Boolean {
                zoomIn(holder.businessImage)
                return true
            }
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                zoomOut(holder.businessImage)
                return super.onSingleTapUp(e)
            }
        })

        holder.businessImage.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    zoomIn(holder.businessImage)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    zoomOut(holder.businessImage)
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return EntertaimentBookList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateBookList(newBookList: List<bookModel>) {
        EntertaimentBookList.clear()
        EntertaimentBookList.addAll(newBookList)
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