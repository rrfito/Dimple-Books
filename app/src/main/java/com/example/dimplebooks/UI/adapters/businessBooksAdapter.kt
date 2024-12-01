package com.example.dimplebooks.UI.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.example.dimplebooks.data.model.bookModel
import com.example.dimplebooks.databinding.BusinessBooksBinding
import com.example.dimplebooks.utils.baseAdapter
import com.example.dimplebooks.utils.baseAdapter.OnItemClickListener

class businessBooksAdapter(
    businessBookList: ArrayList<bookModel>,
    listener: OnItemClickListener<bookModel>
) : baseAdapter<bookModel>(businessBookList, listener) {

    class ViewHolder(val binding: BusinessBooksBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = BusinessBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

                businessTittle.text = if (currentBook.title.length > 30) {
                    "${currentBook.title.take(30)}..."
                } else {
                    currentBook.title
                }

                val priceText = when (currentBook.saleability) {
                    "FOR_SALE" -> "Rp ${currentBook.price}"
                    else -> "NOT FOR SALE"
                }
                price.apply {
                    text = priceText
                    setTextColor(
                        if (priceText == "NOT FOR SALE") {
                            root.context.getColor(R.color.red)
                        } else {
                            root.context.getColor(R.color.orange_white)
                        }
                    )
                }
                rating.text = currentBook.rating.toString()
                setupGesture(cardBusiness, currentBook)
            }
        }
    }
}
