package com.example.anyang_linker.fragments.talk

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.item_chat_list.view.*

class ChatListAdatper(arrayList: ArrayList<ChatListItem>) : RecyclerView.Adapter<ChatListAdatper.ChatListViewHolder>(){

    var item = arrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatListViewHolder((parent))

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ChatListAdatper.ChatListViewHolder, position: Int) {
        holder.txt_chatList_userId.text = item.get(position).roomName
        holder.txt_chatList_preview.text = item.get(position).message
        holder.txt_chatList_time.text = item.get(position).ampm + " " + item.get(position).dateTime

        holder.layout_chatList.setOnClickListener {
            //Log.i("테스트입니다: ", item.get(position).test.toString())
            val intent = Intent(it.context, ChatActivity::class.java)
            intent.putExtra("RoomNumber", item.get(position).roomName.toString())
            it.context.startActivity(intent)
        }
    }

    inner class ChatListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)){

        val txt_chatList_userId = itemView.txt_chatList_userId
        val txt_chatList_preview = itemView.txt_chatList_preview
        val txt_chatList_time = itemView.txt_chatList_time

        val layout_chatList = itemView.layout_chatList
    }
}