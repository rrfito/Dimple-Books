package com.example.dimplebooks.UI.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dimplebooks.R
import com.example.dimplebooks.model.RecycleViewBook
import com.example.dimplebooks.model.bookModel

class bookAdapter(private val bookList: ArrayList<bookModel>) : RecyclerView.Adapter<bookAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookImage: ImageView = itemView.findViewById(R.id.image)
        val bookName: TextView = itemView.findViewById(R.id.bookName)
        val authorName: TextView = itemView.findViewById(R.id.author)
        val price: TextView = itemView.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = bookList[position]
        holder.bookName.text = currentBook.title
        holder.authorName.text = currentBook.authors.joinToString(", ")
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



    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}



