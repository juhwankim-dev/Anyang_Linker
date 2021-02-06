package com.example.anyang_linker.fragments.study.timetable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.study.search.StudySearchResultActivity
import kotlinx.android.synthetic.main.activity_time_select.*

class TimeSelectActivity : AppCompatActivity() {
    var timeTable = Array(45, {false}) // 시간표 정보를 담을 배열

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_select)

        val department = intent.getStringExtra("department")

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
                if(timeTable[i]){ // 선택되었던 칸을 다시 클릭하면
                    VIEWS[i].setBackgroundResource(R.drawable.table_boarder)
                    timeTable[i] = false // false로 정해놓음
                }else{
                    VIEWS[i].setBackgroundResource(R.drawable.table_border_fill_blue)
                    timeTable[i] = true // 클릭되었다는 뜻으로 true 저장
                }
            }
        }

        layout_timeSelectFinish.setOnClickListener {
            if(emptyCheck()){ // 시간표를 하나라도 클릭했다면
                Toast.makeText(this, "시간표 선택이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                var date = ""

                // 시간표를 변환
                for(i in 0..44){
                    if(timeTable[i]) {
                        date += convert(i)
                    }
                }

                // MakeNewGroupActivitiy 에서 호출한 경우
                if(intent.getBooleanExtra("CalledByNewGroup", false)){
                    var intent2 = Intent()
                    intent2.putExtra("date", date.substring(0, date.length-2))
                    setResult(RESULT_OK, intent2);
                    finish()
                } else { // 학과 선택하고 이 페이지로 넘어온 경우
                    // 시간표 정보를 담아 원래 화면으로 보낸다.
                    val goStudySearchResultResultActivity = Intent(this, StudySearchResultActivity::class.java)
                    goStudySearchResultResultActivity.putExtra("date", date.substring(0, date.length-2))
                    goStudySearchResultResultActivity.putExtra("department", department)
                    startActivity(goStudySearchResultResultActivity)
                }
            }

            else{ // 시간표를 클릭하지 않고 완료하기를 누른 경우 (나중에 다이얼로그 띄워서 스킵할건지 묻고 넘기기)
                Toast.makeText(this, "원하는 시간대를 선택해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // 시간표를 저장한 배열을 변환
    fun convert(index: Int): String {
        var str=""

        when(index%5){
            0 -> str = "월"
            1 -> str = "화"
            2 -> str = "수"
            3 -> str = "목"
            4 -> str = "금"
        }

        when(index/5){
            0 -> str += "9"
            1 -> str += "10"
            2 -> str += "11"
            3 -> str += "12"
            4 -> str += "1"
            5 -> str += "2"
            6 -> str += "3"
            7 -> str += "4"
            8 -> str += "5"
        }

        str += ", "
        return str
    }

    private fun emptyCheck(): Boolean {
        for(i in 0..44){
            if(timeTable[i]) return true // 클릭 되어있는 게 한개라도 있으면 true 반환
        }
        return false
    }
}
