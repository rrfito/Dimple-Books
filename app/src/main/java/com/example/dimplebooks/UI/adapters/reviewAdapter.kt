package com.example.dimplebooks.UI.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.example.dimplebooks.data.model.Review
import com.example.dimplebooks.data.model.ShowsReview

class reviewAdapter(
    val commentList: MutableList<ShowsReview>,
    private val listener: onItemClickListener
) : RecyclerView.Adapter<reviewAdapter.CommentViewHolder>() {

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: CircleImageView = itemView.findViewById(R.id.profileReview)
        val userName: TextView = itemView.findViewById(R.id.namepreview)
        val commentText: TextView = itemView.findViewById(R.id.commentpreview)
        val timeAgo: TextView = itemView.findViewById(R.id.timereview)
        val ratingbar: RatingBar = itemView.findViewById(R.id.rate_us_bar)
        val deleteButton: CircleImageView  = itemView.findViewById(R.id.deletereview)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review, parent, false)
        return CommentViewHolder(view)
    }


    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        Glide.with(holder.itemView.context)
            .load(comment.imageUrl)
            .fitCenter()
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.profileImage)
        holder.userName.text = comment.username
        holder.ratingbar.rating = comment.rating
        holder.commentText.text = comment.comment
        holder.timeAgo.text = comment.dateTime

        holder.deleteButton.setOnClickListener {
            listener.onItemClick(position)
        }
    }


    override fun getItemCount(): Int {
        return commentList.size
    }

    fun updateData(newData: List<ShowsReview>) {
        commentList.clear()
        commentList.addAll(newData)
        notifyDataSetChanged()
    }
}

