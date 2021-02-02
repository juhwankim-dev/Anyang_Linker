package com.example.anyang_linker.fragments.study.search

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEachIndexed
import androidx.viewpager.widget.PagerAdapter
import com.example.anyang_linker.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_room_result_search_study.*

class StudySearchResultRoomActivity : AppCompatActivity() {

    var viewList = ArrayList<View>() // 프래그먼트를 저장할 배열

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_result_search_study)

        viewList.add(LayoutInflater.from(applicationContext).inflate(R.layout.fragment_study_result_class, null)) // 프래그먼트를 배열에 저장
        viewList.add(LayoutInflater.from(applicationContext).inflate(R.layout.fragment_study_result_date, null))
        viewList.add(LayoutInflater.from(applicationContext).inflate(R.layout.fragment_study_result_host, null))

        // 뷰페이저에 어댑터 연결
        viewPager.adapter = pagerAdatper()

        tabLayout.setupWithViewPager(viewPager) // 탭과 뷰페이저를 연결시킴
        tabLayout.getTabAt(0)?.setText("수업소개") // 아이콘과 텍스트를 같이 넣고 싶으면 이렇게..
        tabLayout.getTabAt(1)?.setText("일정장소")
        tabLayout.getTabAt(2)?.setText("리더소개")

        //tabLayout.getTabAt(0)?.setCustomView(txt_class)
        //txt_class.setTypeface(null, Typeface.BOLD);

        //tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_apps_yellow_24dp)
        //tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_account_box_black_24dp)

        // 탭의 상태변화 이벤트 리스너
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) { // 탭이 또 다시 선택되었을 때

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { // 탭이 선택되지 않았을 때

            }

            override fun onTabSelected(tab: TabLayout.Tab?) { // 탭이 선택되었을 때

            }
        })

        tabLayout.tabIconTint = resources.getColorStateList(R.color.tab_icon, null)

        btn_talkToLeader.setOnClickListener {

        }
    }

    // 이너클래스로 어댑터 생성성
    inner class pagerAdatper : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any) = view == `object`

        override fun getCount() = viewList.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var curView = viewList[position]
            viewPager.addView(curView)
            return curView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            viewPager.removeView(`object` as View)
        }
    }
}
