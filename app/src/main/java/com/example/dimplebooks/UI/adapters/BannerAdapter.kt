package com.example.dimplebooks.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BannerAdapter(private val bannerList: List<Int>) : RecyclerView.Adapter<BannerAdapter.ImageViewHolder>() {

    private var currentPosition = 0
    private val autoSlideScope = CoroutineScope(Dispatchers.Main + Job())

    init {
        startAutoSlide()
    }
    // ViewHolder class to hold the ImageView reference
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    // onCreateViewHolder to inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        return ImageViewHolder(view)
    }

    // onBindViewHolder to bind the data to the ViewHolder
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(bannerList[position])
    }

    // getItemCount to return the size of the list
    override fun getItemCount(): Int {
        return if (bannerList.isEmpty()) 0 else 1
    }
    private fun startAutoSlide() {
        autoSlideScope.launch {
            while (true) {
                delay(3000)
                currentPosition = (currentPosition + 1) % bannerList.size
                withContext(Dispatchers.Main) {
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun stopAutoSlide() {
        autoSlideScope.cancel()
    }
}
