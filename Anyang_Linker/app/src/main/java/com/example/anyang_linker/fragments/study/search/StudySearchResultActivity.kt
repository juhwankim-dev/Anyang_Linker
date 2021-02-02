package com.example.anyang_linker.fragments.study.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.study.StudyRoom
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_result_search_studies.*
// 조건에 맞는 스터디방을 리사이클러뷰로 출력하는 액티비티
class StudySearchResultActivity : AppCompatActivity() {

    val studyrooms = arrayListOf<StudyRoom>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_search_studies)

        val department = intent.getStringExtra("department")
        val timeTable = intent.getStringExtra("timeTable")

        readFireBaseData(department)
    }

    // 파이어 베이스 읽어오기 (조건에 맞는거만 가져올거임)
    fun readFireBaseData(requestedDepartment: String?) {
        FirebaseDatabase.getInstance().reference
            .child("StudyRoom")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    // 읽어오지 못했을 때
                }

                override fun onDataChange(p0: DataSnapshot) {    // p0가 StudyRoom인거고, p0의 children이 StudyRoom_01, 02 ...

                    studyrooms.clear() // 리스트 섞이지 않게 초기화

                    p0.children.forEach {
                        var map = it.value as Map<String, Any>

                        val department = map["department"].toString()
                        val grade = map["grade"].toString()

                        if((requestedDepartment != department)){ // 요청한 조건에 맞지 않으면 다음 루프
                            return@forEach
                        }

                        val title = map["title"].toString()
                        val subject = map["subject"].toString()
                        val maxPeople = map["maxPeople"].toString()
                        val profile = map["profile"].toString()
                        studyrooms.add(
                            StudyRoom(
                                title,
                                subject,
                                grade,
                                maxPeople,
                                profile
                            )
                        )
                    }

                    /* 매니저랑 어댑터 추가 */
                    studyRecyclerView.adapter = MyStudyAdapter(studyrooms) // 어댑터 생성
                    studyRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                }
            })
    }
}

