package com.example.anyang_linker.fragments.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.home.memo.MemoList
import kotlinx.android.synthetic.main.today_list_item.view.*

/*
class ThisWeekAdapter : RecyclerView.Adapter<ThisWeekAdapter.ThisWeekViewHolder>() {

    val memos = arrayListOf<MemoList>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ThisWeekViewHolder {
        val view = LayoutInflater.from().inflate(R.layout.today_list_item, parent, false)
        return ThisWeekViewHolder(view)
    }

    override fun getItemCount(): Int {
        return memos.size
    }

    override fun onBindViewHolder(holder: ThisWeekAdapter.ThisWeekViewHolder, position: Int) {
        holder.txtToDoToday.text = memos.get(0)
    }

    inner class ThisWeekViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.today_list_item, parent, false)){

        val isChecked_Today = itemView.checkboxToday.isChecked()
        val txtToDoToday = itemView.txtToDoToday
    }
}*/

class ThisWeekAdapter(val cats: List<MemoList>) :
    RecyclerView.Adapter<ThisWeekAdapter.Holder>() {

    override fun getItemCount(): Int {
        return cats.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val nameTv = itemView?.findViewById<TextView>(R.id.itemName)

/*        fun bind(cat: Cat) {
            nameTv?.text = cat.catName
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.today_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //holder?.bind(cats[position])
    }
}
