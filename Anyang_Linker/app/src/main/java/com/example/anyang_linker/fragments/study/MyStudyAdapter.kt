package com.example.anyang_linker.fragments.study

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anyang_linker.R
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.study_list_item.view.*

class MyStudyAdapter(studyrooms: ArrayList<StudyRoom>) : RecyclerView.Adapter<MyStudyAdapter.MyStudyViewHolder>() {

    var studyrooms = arrayListOf<StudyRoom>() // 어댑터 내의 studyrooms
    init {
        this.studyrooms = studyrooms // 어댑터로 넘어온 인자값인 studyrooms(= 클래스 선언부 앞에 써있음, 그거 생성자임)
                                        // 를 어댑터 내부의 studyrooms 로..
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyStudyViewHolder((parent))

    override fun getItemCount(): Int {
        return studyrooms.size
    }

    override fun onBindViewHolder(holder: MyStudyViewHolder, position: Int) {
        /* Glide 라이브러리를 이용해 인터넷을 통해 사진 가져오기 */
        Glide.with(holder.itemView)
            .load(studyrooms.get(position).profile)
            .override(60,60)
            .into(holder.profile)

        Log.i("@@@@@@: ", studyrooms.get(position).profile + "@@@@@@@@@@@@@@@")
        holder.title.text = studyrooms.get(position).title
        holder.subject.text = studyrooms.get(position).subject
        holder.grade.text = studyrooms.get(position).grade + "학년"
        holder.maxPeople.text = "정원 " + studyrooms.get(position).maxPeople + "명"
    }

    inner class MyStudyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.study_list_item, parent, false)){

        val title = itemView.txt_titleOfRoomList // 텍스트뷰의 값을 가져와 저장
        val subject = itemView.txt_subjectOfRoomList // 텍스트뷰의 값을 가져와 저장
        val grade = itemView.txt_gradeOfRoomList
        val maxPeople = itemView.txt_maxPeopleOfRoomList
        val profile = itemView.img_profileOfRoomList // 이미지뷰의 값을 가져와 저장
    }


}



/*
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
)*/
