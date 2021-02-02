package com.example.anyang_linker.fragments.setting

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val requiredPermissions = arrayOf(Manifest.permission.CALL_PHONE) // 매니페스트에 추가한 권한을 배열에 저장

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onStart() {
        super.onStart()

        layout_certifyUniv.setOnClickListener {
            //var goCertifyUnivActivity = Intent(activity, CertifyUnivActivity::class.java)
            //startActivity(goCertifyUnivActivity)
        }

        layout_profileEdit.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 0)
        }

        layout_badge.setOnClickListener {
            var goBadgeActivity = Intent(activity, BadgeActivity::class.java)
            startActivity(goBadgeActivity)
        }

        layout_pushSet.setOnClickListener {
            var goPushSsttingActivity = Intent(activity, PushSettingActivity::class.java)
            startActivity(goPushSsttingActivity)
        }

        layout_customerCenter.setOnClickListener {
            val intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 1077309872))
            requestPermissions(requiredPermissions, 0)
            startActivity(intentCall)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data!=null){
            imageView_profile.setImageURI(data?.data)
       }
    }
}
