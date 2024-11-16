package com.example.dimplebooks.UI.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dimplebooks.R
import com.example.dimplebooks.model.bookModel
import com.example.dimplebooks.model.dailyPicsItemModel

class dailyBookGridAdapter(private val context: Context, private var dailyPics: List<bookModel>): ArrayAdapter<bookModel>(context, 0, dailyPics) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: View.inflate(context, R.layout.daily_pics_item, null)

        val item = getItem(position)
        val imageView = view.findViewById<ImageView>(R.id.dailypics1)
        val textDailypics1 = view.findViewById<TextView>(R.id.textDailypics1)

        item?.let {
            textDailypics1.text =if (it.title.length > 10){
                it.title.take(15) + "..."
            }else{
                it.title
            }
            Glide.with(imageView.context)
                .load(it.imageUrl)
                .fitCenter()
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imageView)
        }


        return  view
    }

}