package com.example.anyang_linker.fragments.study

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.fragment_study.*
import java.lang.Exception

class StudyFragment : Fragment() {

    var inputFlag1 = false
    var inputFlag2 = false
    var inputFlag3 = false

    companion object{
        var department = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_study, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* -------------------------------스피너 구현 -------------------------- */
        val itemsGrade = resources.getStringArray(R.array.array_grade)

        //val myAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, itemsGrade)

        // initialize an array adapter for spinner
        val myAdapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            view.context,
            android.R.layout.simple_spinner_dropdown_item,
            itemsGrade
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                // set item text bold
                //view.setTypeface(view.typeface, Typeface.BOLD)

                // set selected item style
                if (position == spinner_grade.selectedItemPosition && position != 0) {
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                // make hint item color gray
                if (position == 0) {
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }
        }

        val spinnerGrade = view.findViewById(R.id.spinner_grade) as Spinner
        spinnerGrade.adapter = myAdapter
        spinnerGrade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                try{
                    (parent.getChildAt(0) as TextView).setTextColor(Color.GRAY)
                }catch (e: Exception){

                }
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> {

                    }
                    1 -> {

                    }
                    //...
                    else -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        /* -------------------------------스피너 구현 -------------------------- */

    }

    override fun onStart() {
        super.onStart()

        // 학과 입력을 누르면
        textView_department.setOnClickListener {
            var goDepartmentSearchActivity = Intent(activity, DepartmentSearchActivity::class.java)
            startActivityForResult(goDepartmentSearchActivity, 1)
        }

        // 시간표 선택 페이지로 넘어가기
        textView_Day.setOnClickListener {
            var goTimeTableActivity = Intent(activity, TimeSelectActivity::class.java)
            startActivityForResult(goTimeTableActivity, 2)
        }

        // 개설된 스터디들의 목록 보여주러 가기 (SEARCH 버튼을 눌렀을 때)
        btn_search.setOnClickListener {
            val department = textView_department.text.toString()
            val grade = spinner_grade.selectedItem.toString()
            var goAllstudiesActivity = Intent(activity, AllStudiesActivity::class.java)
            goAllstudiesActivity.putExtra("department", department)
            goAllstudiesActivity.putExtra("grade", grade)
            startActivity(goAllstudiesActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            1 -> { // 학과 선택 액티비티를 갔다온 경우
                if(department != ""){
                    textView_department.text = department
                    textView_department.setTextColor(ContextCompat.getColor(context!!, R.color.colorBlack))
                    department = ""
                }else{
                    Toast.makeText(context, "학과를 설정하지 않으셨습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            2 -> { // 시간표 선택 액티비티를 갔다온 경우
                when (resultCode) {
                    Activity.RESULT_OK -> { // 시간표 선택을 완료하고 돌아온 경우

                    }

                    Activity.RESULT_CANCELED -> { // 시간표 선택을 하지 않고 뒤로가기로 빠져나온 경우
                        Toast.makeText(context, "요일을 설정하지 않으셨습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun canISearch(){

    }
}


/*
fragment에서 recyclerview 사용하려면 필요한거
val rootView = inflater.inflate(R.layout.fragment_study, container, false)
var stRV = rootView.findViewById(R.id.studyRecyclerView) as RecyclerView*/
