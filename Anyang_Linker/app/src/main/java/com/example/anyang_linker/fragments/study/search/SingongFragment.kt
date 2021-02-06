package com.example.anyang_linker.fragments.study.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.study.StudyRoom
import com.example.anyang_linker.fragments.study.search.StudySearchResultActivity.Companion.requestedDate
import com.example.anyang_linker.fragments.study.search.StudySearchResultActivity.Companion.requestedDepartment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_singong.*

class SingongFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        readFireBaseData(requestedDepartment, requestedDate)

        return inflater.inflate(R.layout.fragment_singong, container, false)
    }

    // 파이어 베이스 읽어오기 (조건에 맞는거만 가져올거임)
    fun readFireBaseData(requestedDepartment: String?, requestedDate: String?) {

        var studyrooms = arrayListOf<StudyRoom>()

        FirebaseDatabase.getInstance().reference
            .child("group")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    // 읽어오지 못했을 때
                }

                override fun onDataChange(p0: DataSnapshot) {    // p0가 group인거고, p0의 children이 그 밑에 UID..

                    p0.children.forEach {
                        var map = it.value as Map<String, Any>

                        val department = map["department"].toString()
                        val date = map["date"].toString()
                        val type = map["type"].toString()

                        if(type != "신공재전"){ // 신공재전이 아니거나
                            return@forEach
                        }

                        if((requestedDepartment != department)){ // 학과가 맞지 않거나
                            return@forEach
                        }

                        val studydate = date.split(",", " ")
                        if(timeCheck(studydate, requestedDate)){ // 스터디 시간이랑 맞지 않으면
                            //Log.i("검사", requestedDate.toString() + " " + studydate.toString())
                            return@forEach
                        }

                        val grade = map["grade"].toString()
                        val title = map["title"].toString()
                        val subject = map["subject"].toString()
                        val people = map["people"].toString()
                        val profile = "https://firebasestorage.googleapis.com/v0/b/anyanglinker.appspot.com/o/profile.png?alt=media&token=a8c36e85-b7c6-495a-b627-866bd9772bb5"
                        val leaderUID = map["leaderUID"].toString()

                        studyrooms.add(
                                StudyRoom(
                                    title,
                                    subject,
                                    grade,
                                    people,
                                    profile,
                                    leaderUID,
                                    it.ref.key!!
                                )
                            )
                    }

                    /* 매니저랑 어댑터 추가 */
                    studyRecyclerView.adapter = SingongAdapter(studyrooms) // 어댑터 생성
                    studyRecyclerView.layoutManager = LinearLayoutManager(context)
                }
            })
    }

    private fun timeCheck(
        studydate: List<String>,
        requestedDate: String?
    ): Boolean {
        studydate.forEach {
            if(!requestedDate!!.contains(it)){ // 스터디 시간이랑 맞지 않으면
                return true
            }
        }
        return false
    }
}
