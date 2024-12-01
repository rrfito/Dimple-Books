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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.example.dimplebooks.data.model.bookModel
import com.example.dimplebooks.databinding.BannerItemBinding
import com.example.dimplebooks.utils.baseAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class BannerAdapter(
     bannerList: ArrayList<bookModel>,
     listener: OnItemClickListener<bookModel>
) : baseAdapter<bookModel>(bannerList,listener) {

    private var currentPosition = 0
    private val autoSlideScope = CoroutineScope(Dispatchers.Main + Job())


   class ViewHolder(val binding: BannerItemBinding) : RecyclerView.ViewHolder(binding.root)

    // ViewHolder class
    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivBackground: ImageView = itemView.findViewById(R.id.ivBackground)
        val namebanner: TextView = itemView.findViewById(R.id.namebanner)
        val authorbanner: TextView = itemView.findViewById(R.id.authorbanner)
        val descriptionBanner: TextView = itemView.findViewById(R.id.description_banner)
        val cardimage: ImageView = itemView.findViewById(R.id.imagesbanner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = BannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
            val bannerItem = items[position]
            with(holder.binding) {
                Glide.with(root.context)
                    .load(bannerItem.imageUrl)
                    .fitCenter()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(imagesbanner)

                Glide.with(holder.itemView.context)
                    .load(bannerItem.imageUrl)
                    .fitCenter()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(ivBackground)
                namebanner.text = if (bannerItem.title.length > 30) {
                    bannerItem.title.take(30) + "..."
                } else {
                    bannerItem.title
                }
                authorbanner.text = if (bannerItem.authors.joinToString().length > 30) {
                    bannerItem.authors.joinToString().take(30) + "..."
                } else {
                    bannerItem.authors.joinToString()
                }
                descriptionBanner.text = bannerItem.description
            }

        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun startAutoSlide(viewPager: ViewPager2) {
        autoSlideScope.launch {
            while (items.isNotEmpty()) {
                delay(3000)
                currentPosition = (currentPosition + 1) % items.size


                withContext(Dispatchers.Main) {
                    viewPager.setCurrentItem(currentPosition, true)
                }
            }
        }
    }

    fun stopAutoSlide() {
        autoSlideScope.cancel()
    }

}
