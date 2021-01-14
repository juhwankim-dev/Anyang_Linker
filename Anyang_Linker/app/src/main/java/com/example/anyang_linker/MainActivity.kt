package com.example.anyang_linker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.anyang_linker.fragments.*
import com.example.anyang_linker.fragments.home.HomeFragment
import com.example.anyang_linker.fragments.study.StudyFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* -------------------------------프래그먼트 관련--------------------------------*/
        /* 래그먼트 변수*/
        val homeFragment = HomeFragment()
        val studyFragment = StudyFragment()
        val mentoFragment = MentoFragment()
        val groupFragment = GroupFragment()
        val settingsFragment = SettingsFragment()

        // 현재 프래그먼트 화면을 생성하는 메소드
        makeCurrentFragment(homeFragment)

        // 프래그먼트가 클릭 되면 할 행동 (화면 전환)
        nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.navigation_home -> makeCurrentFragment(homeFragment)
                R.id.navigation_study -> makeCurrentFragment(studyFragment)
                R.id.navigation_mento -> makeCurrentFragment(mentoFragment)
                R.id.navigation_group -> makeCurrentFragment(groupFragment)
                R.id.navigation_settings -> makeCurrentFragment(settingsFragment)
            }
            true
        }
        /*-------------------------------------------------------------------------*/
    }

    // 현재 프래그먼트 화면을 생성하는 메소드
    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            commit()
        }
}
