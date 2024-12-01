package com.example.dimplebooks.UI.adapters

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.dimplebooks.data.model.bookModel
import com.example.dimplebooks.databinding.BusinessBooksBinding
import com.example.dimplebooks.databinding.DailyPicsItemBinding
import com.example.dimplebooks.utils.baseAdapter

class dailyBookGridAdapter(
    bookList: ArrayList<bookModel>,
    listener: OnItemClickListener<bookModel>) : baseAdapter<bookModel>(bookList,listener) {

    class ViewHolder(val binding: DailyPicsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DailyPicsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
            val currentBook = items[position]
            with(holder.binding){
                textDailypics1.text = if (currentBook.title.length > 20) {
                    currentBook.title.take(20) + "..."
                } else {
                    currentBook.title
                }
                val seasibility = currentBook.saleability
                if(seasibility == "FOR_SALE"){
                    cardbuynowDaily.visibility = View.VISIBLE
                }else{
                    cardbuynowDaily.visibility = View.GONE
                }
                Glide.with(root.context)
                    .load(currentBook.imageUrl)
                    .fitCenter()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(dailypics1)

                setupGesture(framelayoutzoom, currentBook)
            }


        }

    }
    }






