package com.example.anyang_linker.Intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_go_home.setOnClickListener {
            startActivity(Intent(this, IntroActivity::class.java))
        }

        btn_go_certify_activity.setOnClickListener {
            startActivity(Intent(this, CertifyActivity::class.java))
        }
    }
}
