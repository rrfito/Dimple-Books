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
import com.example.dimplebooks.data.model.bookModel
import com.example.dimplebooks.databinding.RecommendationbooksBinding
import com.example.dimplebooks.utils.baseAdapter
import com.example.dimplebooks.utils.baseAdapter.OnItemClickListener

class goodBooksAdapter(
    goodbooksList: ArrayList<bookModel>,
    listener: OnItemClickListener<bookModel>) : baseAdapter<bookModel>(goodbooksList,listener) {

    class ViewHolder(val binding : RecommendationbooksBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecommendationbooksBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            val currentBook = items[position]
            with(holder.binding){
                subtitleRecommendation.text = if (currentBook.subtitle?.length!! > 50){
                    currentBook.subtitle?.take(50) + "..."
                }else{
                    currentBook.subtitle
                }
                titleRecommendation.text = if (currentBook.title.length > 20) {
                    currentBook.title.take(20) + "..."
                } else {
                    currentBook.title
                }
                Glide.with(root.context)
                    .load(currentBook.imageUrl)
                    .fitCenter()
                    .error(R.drawable.error)
                    .placeholder(R.drawable.loading)
                    .into(imagerecommendation)
                descriptionRecommendation.text = if (currentBook.description.length > 130){
                    currentBook.description.take(130) + "..."

                }else{
                    currentBook.description
                }
                pageRecommendation.text = currentBook.pageCount.toString() + " pages"
                dateRecommendation.text = currentBook.publishedDate
                val seasibility = currentBook.saleability
                if(seasibility == "FOR_SALE"){
                    cardbuynow.visibility = View.VISIBLE
                }else{
                    cardbuynow.visibility = View.GONE
                }
                setupGesture(imagerecommendation, currentBook)


            }

        }
    }


}


