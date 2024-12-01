package com.example.dimplebooks.utils

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.data.model.bookModel

abstract class baseAdapter<T>(
    val items: ArrayList<T>,
    val listener: OnItemClickListener<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    fun updateBookList(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    protected fun setupGesture(view: View, item: T) {
        val gestureDetector = GestureDetector(view.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                listener.onItemClick(item)
                return true
            }

            override fun onDown(e: MotionEvent): Boolean {
                ZoomAnimationUtil.zoomIn(view)
                return true
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                ZoomAnimationUtil.zoomOut(view)
                return super.onSingleTapUp(e)
            }
        })

        view.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> ZoomAnimationUtil.zoomIn(view)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> ZoomAnimationUtil.zoomOut(view)
            }
            true
        }
    }

    override fun getItemCount(): Int = items.size
}

object ZoomAnimationUtil {
    fun zoomIn(view: View, scale: Float = 1.1f, duration: Long = 300) {
        val zoomIn = ScaleAnimation(
            1.0f, scale,
            1.0f, scale,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        zoomIn.fillAfter = true
        zoomIn.duration = duration
        view.startAnimation(zoomIn)
    }

    fun zoomOut(view: View, scale: Float = 1.1f, duration: Long = 300) {
        val zoomOut = ScaleAnimation(
            scale, 1.0f,
            scale, 1.0f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        )
        zoomOut.fillAfter = true
        zoomOut.duration = duration
        view.startAnimation(zoomOut)
    }
}
