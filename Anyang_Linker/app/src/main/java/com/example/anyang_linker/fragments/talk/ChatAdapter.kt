package com.example.anyang_linker.fragments.talk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.talk.TalkFragment.Companion.userId
import kotlinx.android.synthetic.main.item_my_chat.view.chat_Text
import kotlinx.android.synthetic.main.item_my_chat.view.chat_Time
import kotlinx.android.synthetic.main.item_your_chat.view.*
import kotlin.collections.ArrayList

class ChatAdapter(val context: Context, val arrayList: ArrayList<ChatDTO>)
    :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun addItem(item: ChatDTO) {//아이템 추가
        if (arrayList != null) {
            arrayList.add(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        //getItemViewType 에서 뷰타입 1을 리턴받았다면 내채팅레이아웃을 받은 Holder를 리턴
        if(viewType == 1){
            view = LayoutInflater.from(context).inflate(R.layout.item_my_chat, parent, false)
            return Holder(view)
        }
        //getItemViewType 에서 뷰타입 2을 리턴받았다면 상대채팅레이아웃을 받은 Holder2를 리턴
        else{
            view = LayoutInflater.from(context).inflate(R.layout.item_your_chat, parent, false)
            return Holder2(view)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        //onCreateViewHolder에서 리턴받은 뷰홀더가 Holder라면 내채팅, item_my_chat의 뷰들을 초기화 해줌
        if (viewHolder is Holder) {
            viewHolder.chat_Text.setText(arrayList.get(i).message)
            val time = arrayList.get(i).ampm + " " + arrayList.get(i).dateTime
            viewHolder.chat_Time.setText(time)
        }
        //onCreateViewHolder에서 리턴받은 뷰홀더가 Holder2라면 상대의 채팅, item_your_chat의 뷰들을 초기화 해줌
        else if(viewHolder is Holder2) {
            viewHolder.chat_You_Image.setImageResource(R.drawable.profile)
            viewHolder.chat_You_Name.setText(arrayList.get(i).userId)
            viewHolder.chat_You_Text.setText(arrayList.get(i).message)
            val time = arrayList.get(i).ampm + " " + arrayList.get(i).dateTime
            viewHolder.chat_You_Time.setText(time)
        }

    }

    //내가친 채팅 뷰홀더
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //친구목록 모델의 변수들 정의하는부분
        val chat_Text = itemView.chat_Text
        val chat_Time = itemView.chat_Time
    }

    //상대가친 채팅 뷰홀더
    inner class Holder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //친구목록 모델의 변수들 정의하는부분
        val chat_You_Image = itemView.chat_You_Image
        val chat_You_Name = itemView.chat_You_Name
        val chat_You_Text = itemView.chat_You_Text
        val chat_You_Time = itemView.chat_You_Time
    }

    override fun getItemViewType(position: Int): Int {//여기서 뷰타입을 1, 2로 바꿔서 지정해줘야 내채팅 너채팅을 바꾸면서 쌓을 수 있음
        //내 아이디와 arraylist의 name이 같다면 내꺼 아니면 상대꺼
        return if (arrayList.get(position).userId == userId) {
            1
        } else {
            2
        }
    }
}