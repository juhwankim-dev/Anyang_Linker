package com.example.anyang_linker.fragments.home

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.home.memo.AppDatabase
import com.example.anyang_linker.fragments.home.memo.Todo
import kotlinx.android.synthetic.main.activity_todo.view.*
import kotlinx.android.synthetic.main.today_list_item.view.*

class ThisWeekAdapter(val todos: List<Todo>) :
    RecyclerView.Adapter<ThisWeekAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.today_list_item, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.checkboxToday.isChecked = todos.get(position).ischecked
        holder.txtToDoToday.text = todos.get(position).title

        textStatueSetting(holder) // 체크박스 클릭시 텍스트 변화주기

        /* ------------------------------ DB 읽어오기 -------------------------------*/
        val db = Room.databaseBuilder(
            holder.checkboxToday.context,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries()
            .build()
        /* ------------------------------ DB 읽어오기 -------------------------------*/

        // 체크박스 클릭 리스너
        holder.checkboxToday.setOnCheckedChangeListener { buttonView, isChecked ->
            textStatueSetting(holder) // 체크박스 클릭시 텍스트 변화주기

            if(isChecked){ // ischecked 값 업데이트 하기
                db.todoDao().updateByischecked(true, todos.get(position).title)
            }

            else{
                db.todoDao().updateByischecked(false, todos.get(position).title)
            }
        }

        holder.txtToDoToday.setOnLongClickListener {
            Toast.makeText(it.context, "취소 기능을 만들 예정입니다.", Toast.LENGTH_SHORT).show()
            true
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkboxToday = itemView.checkboxToday
        val txtToDoToday = itemView.txtToDoToday
    }

    // 체크박스 클릭시 텍스트 변화주기
    fun textStatueSetting(holder : ThisWeekAdapter.Holder){
        if(holder.checkboxToday.isChecked){
            holder.txtToDoToday.setPaintFlags(holder.txtToDoToday.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG) // 취소선 긋기
            holder.txtToDoToday.setTextColor(ContextCompat.getColor(holder.checkboxToday.context, R.color.colorGray))
        }
        else{
            holder.txtToDoToday.setPaintFlags(0)
            holder.txtToDoToday.setTextColor(ContextCompat.getColor(holder.checkboxToday.context, R.color.colorBlack))
        }
    }
}
