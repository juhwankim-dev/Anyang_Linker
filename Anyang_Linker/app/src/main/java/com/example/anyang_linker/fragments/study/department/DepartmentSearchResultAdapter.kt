package com.example.anyang_linker.fragments.study.department

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.study.timetable.TimeSelectActivity
import kotlinx.android.synthetic.main.department_list_item.view.*

class DepartmentSearchResultAdapter(curList: List<String>) : RecyclerView.Adapter<DepartmentSearchResultAdapter.MyViewHolder>() {

    val items = curList // 필터링된 리스트를 가져옴

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt.text = items.get(position)

        holder.itemView.setOnClickListener { v -> // 리스트에서 학과를 선택하면 데이터를 가지고 studyFragment로 돌아간다.
            //department = items.get(position)
            val goNext = Intent(v.context, TimeSelectActivity::class.java)
            goNext.putExtra("department", items.get(position))
            //goback.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // 액티비티 스택 지워주기
            v.context.startActivity(goNext)
        }
    }

    inner class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.department_list_item, parent, false)){

        val txt = itemView.txt_department_listitem // 아이템 뷰에 있는 텍스트뷰의 값을 가져와 저장
    }

}
