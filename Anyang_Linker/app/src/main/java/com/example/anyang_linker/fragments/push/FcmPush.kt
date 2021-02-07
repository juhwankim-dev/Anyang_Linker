package com.example.anyang_linker.fragments.push

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.squareup.okhttp.*
import java.io.IOException

class FcmPush {
    var JSON = MediaType.parse("application/json; charset=utf-8")//Post전송 JSON Type
    var url = "https://fcm.googleapis.com/fcm/send" //FCM HTTP를 호출하는 URL
    var serverKey = "AAAAYwrmKG4:APA91bE-W2SG8u2EaQCkuEoFYcHPepJs1xi7U194c-zM1OQK2gHDRfc3D9DBlKWq_hdUACQDsUqScfUoYcLk6dmnmyKEiJxlpsns5bAR7PPt5mbqTEYgXMh7GsCc-bFF6ggComSzPDq4"  //Firebase에서 복사한 레거시서버키
    var okHttpClient: OkHttpClient? = null
    var gson: Gson? = null
    companion object{
        var instance = FcmPush()
    }

    init {
        gson = Gson()
        okHttpClient = OkHttpClient()
    }

    fun sendMessage(destinationUid: String, title: String, message: String) {
        FirebaseFirestore.getInstance().collection("pushtokens").document(destinationUid).get()//destinationUid의 값으로 푸시를 보낼 토큰값을 가져오는 코드
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var token = task?.result?.get("pushToken").toString()

                    var pushDTO = PushDTO()
                    pushDTO.to = token                   //푸시토큰 세팅
                    pushDTO.notification?.title = title  //푸시 타이틀 세팅
                    pushDTO.notification?.body = message //푸시 메시지 세팅

                    var body = RequestBody.create(JSON, gson?.toJson(pushDTO))
                    var request = Request
                        .Builder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "key=" + serverKey)
                        .url(url)       //푸시 URL 세팅
                        .post(body)     //pushDTO가 담긴 body 세팅
                        .build()
                    okHttpClient?.newCall(request)?.enqueue(object : Callback {//푸시 전송
                        override fun onFailure(request: Request?, e: IOException?) {

                        }

                        override fun onResponse(response: Response?) {
                            println(response?.body()?.string())  //요청이 성공했을 경우 결과값 출력
                        }
                    })
                }

            }
    }
}
