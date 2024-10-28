package com.example.dimplebooks.UI.basicListView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.dimplebooks.R

class ListAdapter(
    context: Context,
    private val menuList : List<ListModel>
) : ArrayAdapter<ListModel>(context,0,menuList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup)
    : View {
        val view = convertView?:LayoutInflater.from(context).inflate(R.layout.list_view_item,parent,false)

        val menuItem = getItem(position)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val nameView = view.findViewById<TextView>(R.id.textName)
        val textDesc = view.findViewById<TextView>(R.id.textView)

        menuItem?.let {
            imageView.setImageResource(it.imageResId)
            nameView.text = it.name
            textDesc.text = it.name
        }
        return view
    }
}