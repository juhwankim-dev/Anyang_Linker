package com.example.anyang_linker.fragments.setting

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.anyang_linker.Intro.IntroActivity
import com.example.anyang_linker.Intro.UserDTO
import com.example.anyang_linker.MainActivity.Companion.currentUserID
import com.example.anyang_linker.MainActivity.Companion.currentUserName
import com.example.anyang_linker.MainActivity.Companion.currentUserProfile
import com.example.anyang_linker.MainActivity.Companion.currentUserStudentNumber

import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.setting.newgroup.MakeNewGroupActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.fragment_settings.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

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

        updateProfile()

        layout_newgroup.setOnClickListener {
            startActivity(Intent(Intent(activity, MakeNewGroupActivity::class.java)))
        }

        layout_profileEdit.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 0)
        }

        layout_badge.setOnClickListener {
            startActivity(Intent(activity, BadgeActivity::class.java))
        }

        layout_pushSet.setOnClickListener {
            startActivity(Intent(activity, PushSettingActivity::class.java))
        }

        layout_customerCenter.setOnClickListener {
            requestPermissions(requiredPermissions, 0) // 전화 권한 동의
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 1077309872)))
        }

        layout_logout.setOnClickListener {
            val pref = context!!.getSharedPreferences("login", 0)
            pref.edit().clear().commit() // 자동로그인을 위해 저장되어있던 이메일과 비밀번호 삭제
            FirebaseAuth.getInstance().signOut() // 로그아웃
            startActivity(Intent(activity, IntroActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)) // 인트로 페이지로
        }
    }

    // 갤러리에서 사진을 선택해서 프로필 편집하려고 할 때
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data!=null){

            var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var imgFileName = "IMAGE_" + timeStamp
            var storageRef = FirebaseStorage.getInstance().reference.child("images")?.child(imgFileName) // 서버에 이미지 저장

            FirebaseDatabase.getInstance().reference.child("user").child(currentUserID).child("userProfile").setValue(imgFileName)// user 정보에 썸네일 이름 저장
            currentUserProfile = imgFileName // 여기에도 저장해준다. 나중에 톡할때 쓸거임

            // 이미지 파일 파이어베이스에 업로드
            storageRef.putFile(data.data!!).addOnSuccessListener {
                Toast.makeText(context, "프로필이 변경되었습니다", Toast.LENGTH_SHORT).show()
            }

            // 프로필 사진 바꾸기
            Glide.with(context!!).load(data?.data).circleCrop().override(100,100).into(imageView_profile)

            //imageView_profile.setImageURI(data?.data)
       }
    }

    // 회원 정보를 가져와서 프로필을 업데이트
    private fun updateProfile(){
        txt_username.text = currentUserName
        txt_studentNumber.text = currentUserStudentNumber

        if(currentUserProfile == ""){

        }else{
            FirebaseStorage.getInstance().reference.child("images").child(currentUserProfile).downloadUrl.addOnSuccessListener {
                Glide.with(context!!).load(it).circleCrop().override(100,100).into(imageView_profile)
            }
        }
    }
}
