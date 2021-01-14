package com.example.anyang_linker.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anyang_linker.SplashActivity.Companion.notices
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.notice_list_item.view.*

class MyNoticeAdapter : RecyclerView.Adapter<MyNoticeAdapter.MyNoticeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyNoticeViewHolder((parent))

    override fun getItemCount(): Int {
        return notices.size
    }

    override fun onBindViewHolder(holder: MyNoticeAdapter.MyNoticeViewHolder, position: Int) {
        holder.title.text = notices.get(position).title
        holder.info.text = notices.get(position).info
    }

    inner class MyNoticeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.notice_list_item, parent, false)){

        val title = itemView.txtNoticeTitle // 텍스트뷰의 값을 가져와 저장
        val info = itemView.txtNoticeInfo // 텍스트뷰의 값을 가져와 저장
    }
}