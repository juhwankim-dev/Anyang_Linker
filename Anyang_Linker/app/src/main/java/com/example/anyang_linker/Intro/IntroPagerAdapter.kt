package com.example.anyang_linker.Intro

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.layout_intro_pager_item.view.*

class IntroPagerAdapter(context: Context) : RecyclerView.Adapter<IntroPagerAdapter.IntroPagerViewHolder>(){

    val items = arrayListOf<PageItem>(
        PageItem(R.drawable.intro1, context.getString(R.string.introH1), context.getString(R.string.introC1)),
        PageItem(R.drawable.intro2, context.getString(R.string.introH2), context.getString(R.string.introC2)),
        PageItem(R.drawable.intro3, context.getString(R.string.introH3), context.getString(R.string.introC3))
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IntroPagerViewHolder((parent)) // 뷰홀더 생성

    override fun getItemCount(): Int { // 아이템의 총 갯수 반환
        return items.size
    }

    override fun onBindViewHolder(holder: IntroPagerViewHolder, position: Int) { // 생선된 뷰홀더에 데이터 삽입
        holder.intro_pager_item_text_header.text = items.get(position).header
        holder.intro_pager_item_text_content.text = items.get(position).content
        holder.intro_pager_item_imgage.setImageResource(items.get(position).imageSrc)
    }

    inner class IntroPagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.layout_intro_pager_item, parent, false)){

        val intro_pager_item_text_header = itemView.intro_pager_item_text_header
        val intro_pager_item_text_content = itemView.intro_pager_item_text_content
        val intro_pager_item_imgage = itemView.intro_pager_item_imgage
    }

}