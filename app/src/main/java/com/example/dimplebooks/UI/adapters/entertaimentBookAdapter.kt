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
import com.example.dimplebooks.R

import com.example.dimplebooks.data.model.bookModel
import com.example.dimplebooks.databinding.BusinessBooksBinding
import com.example.dimplebooks.utils.baseAdapter

class entertaimentBookAdapter( EntertaimentBookList: ArrayList<bookModel>,
                               listener: OnItemClickListener<bookModel>)
    : baseAdapter<bookModel>(EntertaimentBookList,listener,) {

        class ViewHolder(val binding: BusinessBooksBinding) : RecyclerView.ViewHolder(binding.root)

//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val businessImage = itemView.findViewById<ImageView>(R.id.businessimages)
//        val businesstittle = itemView.findViewById<TextView>(R.id.businessTittle)
//        val price = itemView.findViewById<TextView>(R.id.price)
//        val rating = itemView.findViewById<TextView>(R.id.rating)
//
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): entertaimentBookAdapter.ViewHolder {
        val binding = BusinessBooksBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val currentBook = items[position]
            with(holder.binding) {
                Glide.with(root.context)
                    .load(currentBook.imageUrl)
                    .fitCenter()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(businessimages)
                businessTittle.text = if(currentBook.title.length > 30){
                    currentBook.title.take(30) + "..."
                }else{
                    currentBook.title
                }
                val pricetext = when(currentBook.saleability){
                    "FOR_SALE" -> "Rp ${currentBook.price}"
                    else -> "NOT FOR SALE"
                }
                if(pricetext == "NOT FOR SALE"){
                    price.setTextColor(holder.itemView.context.getColor(R.color.red))
                }else{
                    price.setTextColor(holder.itemView.context.getColor(R.color.orange_white))
                }
                price.text = pricetext
                rating.text = currentBook.rating.toString()
                setupGesture(businessimages, currentBook)

            }
        }
    }


}