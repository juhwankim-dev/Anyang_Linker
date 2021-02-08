package com.example.anyang_linker.fragments.talk

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.anyang_linker.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_talk.*

// 채팅방 리스트 페이지
class TalkFragment : Fragment() {

    private val databaseReference = FirebaseDatabase.getInstance().reference
    val previewList = arrayListOf<ChatListItem>()
    val chatList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getChatUIDList()

        return inflater.inflate(R.layout.fragment_talk, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            showChatList() // 채팅방 리스트 보여주기
        }, 125)

    }

    private fun getChatUIDList() { // 리스트 어댑터 생성 및 세팅

        databaseReference.child("user").child(FirebaseAuth.getInstance().currentUser!!.uid).child("chatList").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded( // 목록 검색 또는 추가 (리스너를 처음 사용 시작할 때, 데이터를 받아옴 여기서는 방 제목을 가져오는거)
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
                chatList.add(dataSnapshot.value.toString()) // 리스트에 저장
            }

            override fun onChildChanged( // 목록 변경 수신 대기
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun showChatList() {
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리

        // chat -> chatUID -> 메시지들...
        databaseReference.child("chat").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded( // 목록 검색 또는 추가 (리스너를 처음 사용 시작할 때, 데이터를 받아옴 여기서는 방 제목을 가져오는거)
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
                //Log.i("왜 못띄워", "@@@@@@@@@@")

                if(chatList.contains(dataSnapshot.key)){
                    //Log.i("야너두?", "@@@@@@@@@@")
                    var ampm = ""
                    var dateTime = ""
                    var message = ""
                    var myProfileUrl = ""
                    var userName = ""

                    // 2를 빼는 이유는 마지막 인덱스에 participants의 목록이 들어가기 때문이다.
                    var lastChildNumber = dataSnapshot.childrenCount.toInt() - 2 // 가장 마지막 메시지 정보만 받아올거야
                    Log.i("머죠", lastChildNumber.toString())
                    Log.i("머죠2", dataSnapshot.children.elementAt(lastChildNumber).value.toString())
                    dataSnapshot.children.elementAt(lastChildNumber).children.forEach { // 마지막 메시지 정보 뽑기
                        when(it.key){
                            "ampm" -> ampm = it.value.toString()
                            "dateTime" -> dateTime = it.value.toString()
                            "message" -> message = it.value.toString()
                            "myProfileUrl" -> myProfileUrl = it.value.toString()
                            "userName" -> userName = it.value.toString()
                        }
                    }

                    previewList.add(ChatListItem(ampm, dateTime, message, myProfileUrl, userName, dataSnapshot.key)) // dataSnapshot.key == chatUID
                    Log.i("엥", previewList.toString())
                    recyclerView_chatList.adapter = ChatListAdatper(previewList)
                    recyclerView_chatList.layoutManager = LinearLayoutManager(context)
                }
            }

            override fun onChildChanged( // 목록 변경 수신 대기
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    // 프래그먼트에 다시 돌아왔을때 이전에 보여준 결과물이 덮어씌어지지 않게
    override fun onDestroyView() {
        super.onDestroyView()

        chatList.clear()
        previewList.clear()
    }
}
