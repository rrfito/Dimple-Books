package com.example.dimplebooks.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.fragment.History

import com.example.dimplebooks.model.bookModel

class newestBookAdapter(private val newestBookList: ArrayList<bookModel>,
                        private val itemclick: OnItemClickListener
)
    : RecyclerView.Adapter<newestBookAdapter.ViewHolder>() {


        interface  OnItemClickListener{
            fun onItemClick(book: bookModel)
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newImage : ImageView = itemView.findViewById(R.id.newestBookImage)
        val newBookName : TextView = itemView.findViewById(R.id.newestBookName)
        val price : TextView = itemView.findViewById(R.id.newestBookPrice)
        val card : CardView = itemView.findViewById(R.id.cardPrice)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newestBookAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newest, parent, false)
        return newestBookAdapter.ViewHolder(view)
    }


    override fun onBindViewHolder(holder: newestBookAdapter.ViewHolder, position: Int) {
        val currentBook = newestBookList[position]
        Glide.with(holder.itemView.context)
            .load(currentBook.imageUrl)
            .fitCenter()
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.newImage)
        holder.newBookName.text = if (currentBook.title.length > 39) {
            currentBook.title.take(39) + "..."
        }else{
            currentBook.title
        }
        val priceText = when (currentBook.saleability) {
            "FOR_SALE" -> "FOR SALE"
            else -> "NOT FOR SALE"
        }
        if(priceText == "NOT FOR SALE"){

            holder.card.setCardBackgroundColor(holder.itemView.context.getColor(R.color.red))
        }else if (priceText == "FOR SALE"){
            holder.card.setCardBackgroundColor(holder.itemView.context.getColor(R.color.orange))
        }
        holder.price.text = priceText

        holder.itemView.setOnClickListener {
            itemclick.onItemClick(currentBook)
        }
    }

    override fun getItemCount(): Int {
        return newestBookList.size
    }
    fun updateBookList(newBookList: List<bookModel>) {
        newestBookList.clear()
        newestBookList.addAll(newBookList)
        notifyDataSetChanged()
    }


}