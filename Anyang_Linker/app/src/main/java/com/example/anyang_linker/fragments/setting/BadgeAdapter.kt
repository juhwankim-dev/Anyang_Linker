package com.example.anyang_linker.fragments.setting

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.anyang_linker.R

class BadgeAdapter(var context: Context, var arrayList: ArrayList<BadgeItem>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = View.inflate(context, R.layout.badge_list_item, null)

        var badgeImage: ImageView = view.findViewById(R.id.imageView_badge)
        var badgeTitle: TextView = view.findViewById(R.id.txt_badgeTitle)

        var badgeItem: BadgeItem = arrayList.get(position)

        badgeImage.setImageResource(badgeItem.badgeImage)
        badgeTitle.text = badgeItem.badgeTitle

        return view
    }

    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }
}