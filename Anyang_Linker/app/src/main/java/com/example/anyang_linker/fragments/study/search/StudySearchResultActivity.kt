package com.example.anyang_linker.fragments.study.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.activity_result_search_studies.*
import com.google.android.material.tabs.TabLayoutMediator

// 조건에 맞는 스터디방을 리사이클러뷰로 출력하는 액티비티
class StudySearchResultActivity : AppCompatActivity() {

    companion object {
        var requestedDepartment = ""
        var requestedDate = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_search_studies)

        requestedDepartment = intent.getStringExtra("department")
        requestedDate = intent.getStringExtra("date")

        init()
        tabLayout.tabIconTint = resources.getColorStateList(R.color.tab_icon, null)
    }

    private fun init() {
        //val tabIconList = arrayListOf(R.drawable.ic_apps_gray_24dp, R.drawable.ic_account_box_gray_24dp)
        val tabTextList = arrayListOf("신공재전", "튜터링")

        viewPager_profile.adapter = CustomFragmentStateAdapter(this)
        TabLayoutMediator(tabLayout, viewPager_profile) {
                tab, position ->
            //tab.setIcon(tabIconList[position])
            tab.text = tabTextList[position]
        }.attach()
    }

    /* 4개의 프래그먼트를 달아줄 어댑터 */
    inner class CustomFragmentStateAdapter(fragmentActivity: FragmentActivity):
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {

            return when(position) {
                0 -> {
                    SingongFragment()
                }

                else -> {
                    TutorFragment()
                }
            }
        }
    }
}

