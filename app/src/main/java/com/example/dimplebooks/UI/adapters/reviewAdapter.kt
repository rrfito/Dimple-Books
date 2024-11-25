package com.example.dimplebooks.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import android.widget.TextView
import com.example.dimplebooks.R
import com.example.dimplebooks.data.model.reviewModel

class reviewAdapter(private val commentList: List<reviewModel>) : RecyclerView.Adapter<reviewAdapter.CommentViewHolder>() {

    // ViewHolder untuk item komentar
    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: CircleImageView = itemView.findViewById(R.id.profileReview)
        val userName: TextView = itemView.findViewById(R.id.namepreview)
        val commentText: TextView = itemView.findViewById(R.id.commentpreview)
        val timeAgo: TextView = itemView.findViewById(R.id.timereview)
    }

    // Menyiapkan tampilan untuk setiap item komentar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review, parent, false)
        return CommentViewHolder(view)
    }

    // Mengikat data komentar ke dalam item view
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]

        holder.profileImage.setImageResource(comment.profileImage) // Set gambar profil
        holder.userName.text = comment.userName // Set nama pengguna
        holder.commentText.text = comment.commentText // Set isi komentar
        holder.timeAgo.text = comment.timeAgo // Set waktu komentar
    }

    // Mengembalikan jumlah item dalam daftar
    override fun getItemCount(): Int {
        return commentList.size
    }
}
