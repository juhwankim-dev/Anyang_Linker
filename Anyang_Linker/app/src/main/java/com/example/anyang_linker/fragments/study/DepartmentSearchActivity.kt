package com.example.anyang_linker.fragments.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.study.StudyFragment.Companion.department
import kotlinx.android.synthetic.main.activity_department_search.*

class DepartmentSearchActivity : AppCompatActivity() {

    val items = arrayListOf<String>(
        "신학과", "기독교교육과", "국어국문학과", "영어언어문화전공", "러시아언어문화전공",
        "중국언어문화전공", "유아교육과", "공연예술전공", "음악전공", "글로벌경영학과",
        "관광경영학과", "행정학과", "공공행정학과", "관광학과", "컴퓨터공학과",
        "정보전기전자공학과", "통계데이터과학전공", "소프트웨어전공", "융합소프트웨어전공",
        "도시정보공학전공", "환경에너지공학전공", "해양바이오공학전공", "디지털미디어디자인전공",
        "화장품발명디자인전공", "식품영양학과"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_search)

        if(department != ""){
            finish()
        }

        searchView_department.isIconified = false // 키보드 올라오게 하기

        // 서치뷰 이벤트 리스너
        searchView_department.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView_department.isIconified = true // 키보드 내려가게 하기
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != ""){
                    val curList = items.filter { x -> x.contains(newText.toString()) }
                    RecyclerView_departmentSearchResult.adapter = DepartmentSearchResultAdapter(curList)
                    RecyclerView_departmentSearchResult.layoutManager = LinearLayoutManager(applicationContext)
                }

                return true
            }
        })

        // 뒤로가기 버튼 눌렀을 때
        imgBtnBackToHome.setOnClickListener {
            finish()
        }
    }
}
