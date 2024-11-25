package com.example.dimplebooks.UI.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.example.dimplebooks.databinding.ItemBookBinding
import com.example.dimplebooks.data.model.bookModel

class bookAdapter(
    private val bookList: ArrayList<bookModel>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<bookAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(book: bookModel)
    }
    class ViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = bookList[position]

        holder.binding.apply {

            bookName.text = if (currentBook.title.length > 15) {
                "${currentBook.title.take(15)}..."
            } else {
                currentBook.title
            }
            author.text = if (currentBook.authors.joinToString(",").length > 15) {
                "${currentBook.authors.joinToString(",").take(15)}..."
            } else {
                currentBook.authors.joinToString(",")
            }
            val priceText = when (currentBook.saleability) {
                "FOR_SALE" -> "Rp ${currentBook.price}"
                else -> "NOT FOR SALE"
            }
            price.text = priceText
            price.setTextColor(
                if (priceText == "NOT FOR SALE") {
                    root.context.getColor(R.color.red)
                } else {
                    root.context.getColor(R.color.orange_white)
                }
            )
            rating.text = currentBook.rating.toString()
            Glide.with(root.context)
                .load(currentBook.imageUrl)
                .fitCenter()
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(image)

            root.setOnClickListener {
                listener.onItemClick(currentBook)
            }
            val gestureDetector = GestureDetector(root.context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    listener.onItemClick(currentBook)
                    return true
                }

                override fun onDown(e: MotionEvent): Boolean {
                    zoomIn(card)
                    return true
                }

                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    zoomOut(card)
                    return super.onSingleTapUp(e)
                }
            })

            card.setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> zoomIn(card)
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> zoomOut(card)
                }
                true
            }
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
