package com.example.anyang_linker.fragments.setting

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.activity_certify_univ.*


class CertifyUnivActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certify_univ)

        button_inputEmail.setOnClickListener {
            if(editText_email.text.contains("@anyang.ac.kr")){

                // 서버에게 인증 이메일을 요청

                Toast.makeText(this, "인증 메일을 보냈습니다.", Toast.LENGTH_SHORT).show()
            }

            else{
                Toast.makeText(this, "올바른 이메일 주소를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
