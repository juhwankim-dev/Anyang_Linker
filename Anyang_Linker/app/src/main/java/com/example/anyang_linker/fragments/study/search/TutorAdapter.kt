package com.example.anyang_linker.fragments.study.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.study.StudyRoom
import com.example.anyang_linker.fragments.study.search.group.StudySearchResultRoomActivity
import kotlinx.android.synthetic.main.study_list_item.view.*

class TutorAdapter(studyrooms: ArrayList<StudyRoom>) : RecyclerView.Adapter<TutorAdapter.MyStudyViewHolder>() {

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
  /*      Glide.with(holder.itemView)
            .load(studyrooms.get(position).profile)
            .override(60,60)
            .into(holder.profile)*/
        Glide.with(holder.itemView.context).load(studyrooms.get(position).leaderProfileUrl).circleCrop().override(60,60).into(holder.profile)

        holder.title.text = studyrooms.get(position).title
        holder.subject.text = studyrooms.get(position).subject
        holder.grade.text = studyrooms.get(position).grade + "학년"
        holder.maxPeople.text = "정원 " + studyrooms.get(position).people + "명"

        holder.itemView.setOnClickListener {v->
            val goRoom = Intent(v.context, StudySearchResultRoomActivity::class.java)
            goRoom.putExtra("leaderUID", studyrooms.get(position).leaderUID)
            v.context.startActivity(goRoom)
        }
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