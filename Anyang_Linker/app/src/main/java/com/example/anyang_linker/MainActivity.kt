package com.example.anyang_linker

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.anyang_linker.fragments.home.HomeFragment
import com.example.anyang_linker.fragments.setting.SettingsFragment
import com.example.anyang_linker.fragments.study.StudyFragment
import com.example.anyang_linker.fragments.talk.TalkFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_web_view.*

class MainActivity : AppCompatActivity() {

    var time3: Long = 0

    companion object{
        var currentUserID = ""
        var currentUserName = ""
        var currentUserStudentNumber = ""
        var currentUserProfile = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* -------------------------------프래그먼트 관련--------------------------------*/
        /* 래그먼트 변수*/
        val homeFragment = HomeFragment()
        val studyFragment = StudyFragment()
        val talkFragment = TalkFragment()
        val settingsFragment = SettingsFragment()

        // 현재 프래그먼트 화면을 생성하는 메소드
        makeCurrentFragment(homeFragment)

        // 프래그먼트가 클릭 되면 할 행동 (화면 전환)
        nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.navigation_home -> makeCurrentFragment(homeFragment)
                R.id.navigation_study -> makeCurrentFragment(studyFragment)
                R.id.navigation_group -> makeCurrentFragment(talkFragment)
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

    // 뒤로가기 눌렀을 때
    override fun onBackPressed() {
        val time1 = System.currentTimeMillis()
        val time2 = time1 - time3
        if (time2 in 0..2000) {
            moveTaskToBack(true);						// 태스크를 백그라운드로 이동
            finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
            android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
        }
        else {
            time3 = time1
            Toast.makeText(applicationContext, "한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show()
        }
    }
}
