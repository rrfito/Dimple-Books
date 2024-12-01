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
import com.example.dimplebooks.UI.adapters.businessBooksAdapter.ViewHolder

import com.example.dimplebooks.data.model.bookModel
import com.example.dimplebooks.databinding.BusinessBooksBinding
import com.example.dimplebooks.databinding.ItemNewestBinding
import com.example.dimplebooks.utils.baseAdapter

class newestBookAdapter(newestBookList: ArrayList<bookModel>,
                        itemclick: OnItemClickListener<bookModel>
)
    :baseAdapter<bookModel>(newestBookList,itemclick) {


    class ViewHolder(val binding: ItemNewestBinding) : RecyclerView.ViewHolder(binding.root)

    //    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val newImage : ImageView = itemView.findViewById(R.id.newestBookImage)
//        val newBookName : TextView = itemView.findViewById(R.id.newestBookName)
//        val price : TextView = itemView.findViewById(R.id.newestBookPrice)
//        val card : CardView = itemView.findViewById(R.id.cardPrice)
//
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemNewestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                    .into(newestBookImage)
                newestBookName.text = if (currentBook.title.length > 39) {
                    currentBook.title.take(39) + "..."
                } else {
                    currentBook.title
                }
                val priceText = when (currentBook.saleability) {
                    "FOR_SALE" -> "FOR SALE"
                    else -> "NOT FOR SALE"
                }
                if (priceText == "NOT FOR SALE") {

                    cardPrice.setCardBackgroundColor(holder.itemView.context.getColor(R.color.red))
                } else if (priceText == "FOR SALE") {
                    cardPrice.setCardBackgroundColor(holder.itemView.context.getColor(R.color.orange))
                }
                newestBookPrice.text = priceText
                setupGesture(newestBookImage, currentBook)
            }

        }

    }
}


