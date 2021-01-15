package com.example.anyang_linker.fragments.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.activity_push_setting.*

class PushSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push_setting)

        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                Toast.makeText(this, "알림을 설정하였습니다", Toast.LENGTH_SHORT).show()
            }

            else{
                Toast.makeText(this, "알림을 해제하였습니다", Toast.LENGTH_SHORT).show()
            }
        }

        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                Toast.makeText(this, "알림을 설정하였습니다", Toast.LENGTH_SHORT).show()
            }

            else{
                Toast.makeText(this, "알림을 해제하였습니다", Toast.LENGTH_SHORT).show()
            }
        }

        switch3.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                Toast.makeText(this, "알림을 설정하였습니다", Toast.LENGTH_SHORT).show()
            }

            else{
                Toast.makeText(this, "알림을 해제하였습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
