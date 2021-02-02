package com.example.anyang_linker.fragments.talk

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2

import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.home.BannerItem
import com.example.anyang_linker.fragments.home.BannerViewPagerAdapter
import com.example.anyang_linker.fragments.home.MainActivityViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_study.*
import kotlinx.android.synthetic.main.fragment_talk.*

class TalkFragment : Fragment() {

    private val firebaseDatabase = FirebaseDatabase.getInstance() // 데이터베이스 생성
    private val databaseReference = firebaseDatabase.reference

    companion object {
        lateinit var userId: String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_talk, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = "김주환"

        // 방을 새로 생성하고 싶은 경우
        user_next.setOnClickListener {
            val intent = Intent(it.context, ChatActivity::class.java) // 채팅 액티비티로 이동
            intent.putExtra("chatName", user_chat.text.toString())
            startActivity(intent) // 출발
        }
    }

    private fun showChatList() { // 리스트 어댑터 생성 및 세팅
        val arrayList = arrayListOf<ChatListItem>()
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리

        databaseReference.child("chat").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded( // 목록 검색 또는 추가 (리스너를 처음 사용 시작할 때, 데이터를 받아옴 여기서는 방 제목을 가져오는거)
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
                var lastChildNumber = dataSnapshot.childrenCount.toInt() - 1

                val chatPreview = arrayListOf<String>()
                for (i in 0..3) { // ampm, dateTiem, message, userId
                    chatPreview.add(dataSnapshot.children.elementAt(lastChildNumber).children.elementAt(i).value.toString())
                }

                arrayList.add(
                    ChatListItem(
                        chatPreview.get(3),
                        chatPreview.get(2),
                        chatPreview.get(0),
                        chatPreview.get(1),
                        dataSnapshot.key
                    )
                )

                recyclerView_chatList.adapter = ChatListAdatper(arrayList)
                recyclerView_chatList.layoutManager = LinearLayoutManager(context)
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

    override fun onResume() {
        super.onResume()

        showChatList() // 채팅방 리스트 보여주기기
    }
}
