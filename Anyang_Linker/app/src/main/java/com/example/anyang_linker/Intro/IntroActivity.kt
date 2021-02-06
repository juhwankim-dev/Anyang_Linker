package com.example.anyang_linker.Intro

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.anyang_linker.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_intro.*


class IntroActivity : AppCompatActivity() {

    //뒤로가기 연속 클릭 대기 시간
    var mBackWait:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        viewPager_intro.apply {
            adapter = IntroPagerAdapter(context)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            spring_dots_indicator.setViewPager2(this) // 인디케이터랑 뷰페이저를 연결 (this는 context의미하는거 아님! 뷰페이저2 말하는거임)
        }

        btn_go_register_activity.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btn_go_login_activity.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // 텍스트에 밑줄 넣기기
        val mystring = "이미 아이디가 있으신가요?"
        val content = SpannableString(mystring)
        content.setSpan(UnderlineSpan(), 0, mystring.length, 0)
        btn_go_login_activity.setText(content)
    }

    override fun onBackPressed() {
        // 뒤로가기 버튼 클릭
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Snackbar.make(layout_intro,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Snackbar.LENGTH_LONG).show()
        } else {
            finish() //액티비티 종료
        }
    }
}









