package com.example.anyang_linker.Intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_goLoginToHome.setOnClickListener {
            startActivity(Intent(this, IntroActivity::class.java))
        }

        btn_login.setOnClickListener {
            // 로그인누르면...
        }
    }
}
