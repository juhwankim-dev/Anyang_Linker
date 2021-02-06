package com.example.anyang_linker.fragments.talk

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.item_chat_list.view.*

class ChatListAdatper(arrayList: ArrayList<ChatListItem>) : RecyclerView.Adapter<ChatListAdatper.ChatListViewHolder>(){

    var item = arrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatListViewHolder((parent))

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ChatListAdatper.ChatListViewHolder, position: Int) {
        holder.txt_chatList_userName.text = item.get(position).userName
        holder.txt_chatList_message.text = item.get(position).message
        holder.txt_chatList_time.text = item.get(position).ampm + " " + item.get(position).dateTime
        Glide.with(holder.itemView.context).load(item.get(position).myProfileUrl).circleCrop().override(100,100).into(holder.img_chatList_profile)

        holder.layout_chatList.setOnClickListener {
            val intent = Intent(it.context, ChatActivity::class.java)
            intent.putExtra("chatUID", item.get(position).chatUID) // 방 UID 넘겨주기
            it.context.startActivity(intent)
        }
    }

    inner class ChatListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)){

        val txt_chatList_userName = itemView.txt_chatList_userName
        val txt_chatList_message = itemView.txt_chatList_message
        val txt_chatList_time = itemView.txt_chatList_time
        val img_chatList_profile = itemView.img_chatList_profile

        val layout_chatList = itemView.layout_chatList
    }
}