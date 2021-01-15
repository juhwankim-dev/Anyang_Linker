package com.example.anyang_linker.fragments.study

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.activity_time_select.*

class TimeSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_select)

        var isChecked = false // 시간표 선택을 하나로 했는지 체크
        var timeTable = Array(44, {false}) // 시간표 정보를 담을 배열
        var VIEWS : ArrayList<View> = arrayListOf( // 시간표 (뷰들)
            findViewById(R.id.view1), findViewById(R.id.view2), findViewById(R.id.view3), findViewById(R.id.view4), findViewById(R.id.view5),
            findViewById(R.id.view6), findViewById(R.id.view7), findViewById(R.id.view8), findViewById(R.id.view9), findViewById(R.id.view10),
            findViewById(R.id.view11), findViewById(R.id.view12), findViewById(R.id.view13), findViewById(R.id.view14), findViewById(R.id.view15),
            findViewById(R.id.view16), findViewById(R.id.view17), findViewById(R.id.view18), findViewById(R.id.view19), findViewById(R.id.view20),
            findViewById(R.id.view21), findViewById(R.id.view22), findViewById(R.id.view23), findViewById(R.id.view24), findViewById(R.id.view25),
            findViewById(R.id.view26), findViewById(R.id.view27), findViewById(R.id.view28), findViewById(R.id.view29), findViewById(R.id.view30),
            findViewById(R.id.view31), findViewById(R.id.view32), findViewById(R.id.view33), findViewById(R.id.view34), findViewById(R.id.view35),
            findViewById(R.id.view36), findViewById(R.id.view37), findViewById(R.id.view38), findViewById(R.id.view39), findViewById(R.id.view40),
            findViewById(R.id.view41), findViewById(R.id.view42), findViewById(R.id.view43), findViewById(R.id.view44), findViewById(R.id.view45)
            )

        for(i in 0..44){ // 클릭이벤트를 다 달아준다.
            VIEWS[i].setOnClickListener {
                VIEWS[i].setBackgroundColor(ContextCompat.getColor(this, R.color.mainBlue)) // 클릭하면 배경 이미지 색을 바꿈
                timeTable[i] = true // 클릭되었다는 뜻으로 true 저장
                isChecked = true // 하나라도 클릭이 되었는지 체크
            }
        }

        layout_timeSelectFinish.setOnClickListener {
            if(isChecked){ // 시간표를 하나라도 클릭했다면
                Toast.makeText(this, "시간표 선택이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                // 시간표 정보를 담아 원래 화면으로 보낸다.
                val intentTimeTable = Intent(this, StudyFragment::class.java)
                intentTimeTable.putExtra("timeTable", timeTable)
                setResult(Activity.RESULT_OK)
                finish()
            }

            else{ // 시간표를 클릭하지 않고 완료하기를 누른 경우
                Toast.makeText(this, "원하는 시간대를 선택해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
