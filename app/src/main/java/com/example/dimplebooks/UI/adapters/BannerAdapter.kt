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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class BannerAdapter(
    private val bannerList: ArrayList<bookModel>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    private var currentPosition = 0
    private val autoSlideScope = CoroutineScope(Dispatchers.Main + Job())


    interface OnItemClickListener {
        fun onItemClick(book: bookModel)
    }

    // ViewHolder class
    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivBackground: ImageView = itemView.findViewById(R.id.ivBackground)
        val namebanner: TextView = itemView.findViewById(R.id.namebanner)
        val authorbanner: TextView = itemView.findViewById(R.id.authorbanner)
        val descriptionBanner: TextView = itemView.findViewById(R.id.description_banner)
        val cardimage: ImageView = itemView.findViewById(R.id.imagesbanner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val bannerItem = bannerList[position]

        Glide.with(holder.itemView.context)
            .load(bannerItem.imageUrl)
            .fitCenter()
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.cardimage)

        Glide.with(holder.itemView.context)
            .load(bannerItem.imageUrl)
            .fitCenter()
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.ivBackground)
        holder.namebanner.text = if (bannerItem.title.length > 30) {
            bannerItem.title.take(30) + "..."
        } else {
            bannerItem.title
        }
        holder.authorbanner.text = if (bannerItem.authors.joinToString().length > 30) {
            bannerItem.authors.joinToString().take(30) + "..."
        } else {
            bannerItem.authors.joinToString()
        }
        holder.descriptionBanner.text = bannerItem.description

        holder.itemView.setOnClickListener {
            listener.onItemClick(bannerItem)
        }

        val gestureDetector = GestureDetector(holder.itemView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                listener.onItemClick(bannerItem)
                return true
            }
            override fun onDown(e: MotionEvent): Boolean {
                zoomIn(holder.cardimage)
                return true
            }
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                zoomOut(holder.cardimage)
                return super.onSingleTapUp(e)
            }
        })

        holder.cardimage.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    zoomIn(holder.cardimage)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    zoomOut(holder.cardimage)
                }
            }
            true
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateBookList(newBooks: List<bookModel>) {
        bannerList.clear()
        bannerList.addAll(newBooks)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = bannerList.size

    fun startAutoSlide(viewPager: ViewPager2) {
        autoSlideScope.launch {
            while (bannerList.isNotEmpty()) {
                delay(3000)
                currentPosition = (currentPosition + 1) % bannerList.size


                withContext(Dispatchers.Main) {
                    viewPager.setCurrentItem(currentPosition, true)
                }
            }
        }
    }

    fun stopAutoSlide() {
        autoSlideScope.cancel()
    }
    private fun zoomIn(view: View) {
        val zoomIn = ScaleAnimation(
            1.0f, 1.5f,
            1.0f, 1.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        zoomIn.fillAfter = true
        zoomIn.duration = 300
        view.startAnimation(zoomIn)
    }

    private fun zoomOut(view: View) {
        val zoomOut = ScaleAnimation(
            1.5f, 1.0f,
            1.5f, 1.0f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        zoomOut.fillAfter = true
        zoomOut.duration = 300
        view.startAnimation(zoomOut)
    }
}
