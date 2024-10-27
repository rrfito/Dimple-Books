package com.example.dimplebooks.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dimplebooks.R
import com.example.dimplebooks.UI.adapters.bookAdapter.ViewHolder
import java.util.Locale.Category

class categoriesAdapter(private val categories : List<String>,private val listener: OnCategoryClickListener)
    : RecyclerView.Adapter<categoriesAdapter.CategoriesViewHolder>()  {

    interface OnCategoryClickListener {
        fun onCategoryClick(category: String)
    }
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryTextView: TextView = itemView.findViewById(R.id.textCategories)

        fun bind(category: String) {
            categoryTextView.text = category
            categoryTextView.setTextColor(
                if (adapterPosition == selectedPosition) {
                    ContextCompat.getColor(itemView.context, R.color.blue_secondary)
                } else {
                    ContextCompat.getColor(itemView.context,R.color.black)
                }
            )
            itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                listener.onCategoryClick(category)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_categories, parent, false)
        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}