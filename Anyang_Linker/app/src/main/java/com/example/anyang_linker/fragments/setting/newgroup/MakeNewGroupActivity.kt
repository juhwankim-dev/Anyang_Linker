package com.example.anyang_linker.fragments.setting.newgroup

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.anyang_linker.MainActivity.Companion.currentUserID
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.study.department.DepartmentSearchActivity
import com.example.anyang_linker.fragments.study.timetable.TimeSelectActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_make_new_group.*

class MakeNewGroupActivity : AppCompatActivity() {

    var grade: String? = null
    var people: String? = null
    var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_new_group)

        // 완료 버튼 클릭
        text_finish.setOnClickListener {
            groupInfoUpdate()
            finish()
        }

        // 학과 선택 클릭
        txt_department.setOnClickListener {
            var intent = Intent(this, DepartmentSearchActivity::class.java)
            intent.putExtra("CalledByNewGroup", true)
            startActivityForResult(intent, 1)
        }

        // 일정 선택 클릭
        txt_date.setOnClickListener {
            var intent = Intent(this, TimeSelectActivity::class.java)
            intent.putExtra("CalledByNewGroup", true)
            startActivityForResult(intent, 2)
        }

        radioGroup_grade.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioButton -> grade = "1"
                R.id.radioButton2 -> grade = "2"
                R.id.radioButton3 -> grade = "3"
                R.id.radioButton4 -> grade = "4"
            }
        }

        radioGroup_people.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioButton_people_3 -> people = "3"
                R.id.radioButton_people_4 -> people = "4"
                R.id.radioButton_people_5 -> people = "5"
                R.id.radioButton_people_6 -> people = "6"
            }
        }

        radioGroup_type.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioButton_type1 -> type = "신공재전"
                R.id.radioButton_type2 -> type = "튜터링"
            }
        }
    }
    fun groupInfoUpdate(){
        var leaderUID = currentUserID
        var subject = editText_subject.text.toString()
        var place = editText_place.text.toString()
        var department= txt_department.text.toString()
        var date= txt_date.text.toString()

        var introduce= editText_introduce.text.toString()
        var introduce_date= editText_introduce_date.text.toString()
        var introduce_place= editText_introduce_place.text.toString()
        var introduce_leader= editText_introduce_leader.text.toString()
        var title= editText_title.text.toString()

        var groupInfo = GroupDTO(leaderUID, subject, place, department, date, grade, people, type, introduce, introduce_date, introduce_place, introduce_leader, title)

        var databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child("group").push().setValue(groupInfo)
        Toast.makeText(this, "정상적으로 신청되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            1 -> { // 학과 선택하고 돌아온 경우
                val department = data!!.getStringExtra("department")
                txt_department.text = department
                txt_department.setTextColor(Color.parseColor("#000000"));
            }
            
            2 -> { // 시간표 선택하고 돌아온 경우
                val date = data!!.getStringExtra("date")
                txt_date.text = date
                txt_date.setTextColor(Color.parseColor("#000000"));
            }
        }
    }
}
