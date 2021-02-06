package com.example.anyang_linker.fragments.study

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.anyang_linker.MainActivity
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.study.department.DepartmentSearchActivity
import kotlinx.android.synthetic.main.fragment_study.*

class StudyFragment : Fragment() {

/*    companion object{
        var department = ""
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_study, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* ---------------------------스터디 뷰페이저----------------------------- */
        val dpValue = 20
        val d = resources.displayMetrics.density
        val margin = (dpValue * d).toInt()

        viewPagerSetting(viewPager_deadline, margin)
        viewPagerSetting(viewPager_TOEIC, margin)
        viewPagerSetting(viewPager_certify, margin)

        val adapter = DeadlineViewPagerAdapter(view.context)
        viewPager_deadline.adapter = adapter

        val adapter2 = ToeicViewPagerAdapter(view.context)
        viewPager_TOEIC.adapter = adapter2

        val adapter3 = CertifyViewPagerAdapter(view.context)
        viewPager_certify.adapter = adapter3
        /* ---------------------------스터디 뷰페이저----------------------------- */

        val goDepartmentSearchActivity = Intent(context, DepartmentSearchActivity::class.java)

        btn_studySearch.setOnClickListener {
            //val goDepartmentSearchActivity = Intent(context, DepartmentSearchActivity::class.java)
            startActivity(goDepartmentSearchActivity)
        }
    }

    // 스터디 뷰페이저저
   fun viewPagerSetting(viewPager: ViewPager, margin: Int){
        viewPager.setClipToPadding(false)
        viewPager.setPadding(margin, 0, margin*4, 0)
        viewPager.setPageMargin(margin / 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){
            Activity.RESULT_CANCELED -> { // 뒤로가기로 빠져나왔을 경우..

            }
        }
    }
}


/*
fragment에서 recyclerview 사용하려면 필요한거
val rootView = inflater.inflate(R.layout.fragment_study, container, false)
var stRV = rootView.findViewById(R.id.studyRecyclerView) as RecyclerView*/
