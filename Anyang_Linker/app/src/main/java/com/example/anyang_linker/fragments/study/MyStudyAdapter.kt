package com.example.anyang_linker.fragments.study

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.study_list_item.view.*

class MyStudyAdapter : RecyclerView.Adapter<MyStudyAdapter.MyStudyViewHolder>() {

    val rooms = arrayListOf<StudyRoom>(
        StudyRoom(
            "Java 프로그래밍 스터디원 모집",
            " 컴퓨터공학과 | 3학년 | 정원 4명",
            R.drawable.ic_home_black_24dp
        ),
        StudyRoom(
            "C++ 프로그래밍 스터디원 모집",
            " 컴퓨터공학과 | 3학년 | 정원 4명",
            R.drawable.ic_home_black_24dp
        ),
        StudyRoom(
            "안드로이드 프로그래밍 스터디원 모집",
            " 컴퓨터공학과 | 3학년 | 정원 3",
            R.drawable.ic_home_black_24dp
        ),
        StudyRoom(
            "시프 스터디원 모집",
            " 컴퓨터공학과 | 3학년 | 정원 4명",
            R.drawable.ic_home_black_24dp
        ),
        StudyRoom(
            "리눅스 스터디원 모집",
            " 컴퓨터공학과 | 3학년 | 정원 3명",
            R.drawable.ic_home_black_24dp
        ),
        StudyRoom(
            "이산수학 스터디원 모집",
            " 컴퓨터공학과 | 3학년 | 정원 4명",
            R.drawable.ic_home_black_24dp
        ),
        StudyRoom(
            "확률과 통계 스터디원 모집",
            " 통계학과 | 2학년 | 정원 5명",
            R.drawable.ic_home_black_24dp
        ),
        StudyRoom(
            "PPT발표 스터디원 모집",
            " 언론학과 | 3학년 | 정원 4명",
            R.drawable.ic_home_black_24dp
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyStudyViewHolder((parent))

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: MyStudyViewHolder, position: Int) {
        holder.title.text = rooms.get(position).title
        holder.info.text = rooms.get(position).info
        holder.profile.setImageResource(rooms.get(position).profile)
    }

    inner class MyStudyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.study_list_item, parent, false)){

        val title = itemView.txtStudyRoomTitle // 텍스트뷰의 값을 가져와 저장
        val info = itemView.txtStudyRoomInfo // 텍스트뷰의 값을 가져와 저장
        val profile = itemView.imgStudyRoomProfile // 이미지뷰의 값을 가져와 저장
    }


}
