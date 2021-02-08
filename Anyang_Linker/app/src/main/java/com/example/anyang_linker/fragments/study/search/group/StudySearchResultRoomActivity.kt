package com.example.anyang_linker.fragments.study.search.group

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.talk.ChatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_room_result_search_study.*

class StudySearchResultRoomActivity : AppCompatActivity() {

    companion object{
        var requestedMap = mutableMapOf<String, String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_result_search_study)

        var leaderUID = intent.getStringExtra("leaderUID")
        var groupUID = intent.getStringExtra("groupUID")

        // 스터디의 정보를 받아오기
        readFireBaseData(groupUID)

        init() // 프래그먼트 만들고 붙이기
        tabLayout_group.tabIconTint = resources.getColorStateList(R.color.tab_icon, null)

        // 톡 남기기 클릭했을 때
        btn_talkToLeader.setOnClickListener {
            val goChatActivity = Intent(this, ChatActivity::class.java)
            goChatActivity.putExtra("leaderUID", leaderUID)
            startActivity(goChatActivity)
        }
    }

    private fun readFireBaseData(requestedGroupUID: String?) {
        val databaseReference = FirebaseDatabase.getInstance().reference

        // 클릭한 방의 UID에 안에있는 정보를 가져온다.
        databaseReference.child("group").child(requestedGroupUID!!).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(
                dataSnapshot: DataSnapshot,
                s: String?
            ) {
                when(dataSnapshot.key){
                    // title은 여기 액티비티의 xml이라 바로 수정 가능
                    "leaderProfileUrl" -> Glide.with(applicationContext).load(dataSnapshot.value.toString()).circleCrop().override(60,60).into(img_group_leader_profile)
                    "leaderStudentNumber" -> txt_group_student_number.text = dataSnapshot.value.toString() + " 김주환"
                    "title" -> txt_group_title.text = dataSnapshot.value.toString()

                    // 나머지는 map에 담아 각각의 fragment로 보낸다.
                    "place" -> requestedMap["place"] = dataSnapshot.value.toString()
                    "date" ->  requestedMap["date"] = dataSnapshot.value.toString()
                    "people" ->  requestedMap["people"] = dataSnapshot.value.toString()
                    "subject" ->  requestedMap["subject"]= dataSnapshot.value.toString()
                    "introduce" ->  requestedMap["introduce"] = dataSnapshot.value.toString()

                    "introduce_date" ->  requestedMap["introduce_date"] = dataSnapshot.value.toString()
                    "introduce_place" ->  requestedMap["introduce_place"] = dataSnapshot.value.toString()
                    "introduce_leader" ->  requestedMap["introduce_leader"] = dataSnapshot.value.toString()
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun init() {
        //val tabIconList = arrayListOf(R.drawable.ic_apps_gray_24dp, R.drawable.ic_account_box_gray_24dp)
        val tabTextList = arrayListOf("수업소개", "일정안내", "리더소개")

        viewPager_group.adapter = CustomFragmentStateAdapter(this)
        TabLayoutMediator(tabLayout_group, viewPager_group) {
                tab, position ->
            //tab.setIcon(tabIconList[position])
            tab.text = tabTextList[position]
        }.attach()
    }

    /* 4개의 프래그먼트를 달아줄 어댑터 */
    inner class CustomFragmentStateAdapter(fragmentActivity: FragmentActivity):
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> GroupFragment()
                1 -> DateFragment()
                else -> LeaderFragment()
            }
        }
    }
}



/*FirebaseDatabase.getInstance().reference
    .child("group")
    .addValueEventListener(object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            // 읽어오지 못했을 때
        }

        override fun onDataChange(p0: DataSnapshot) {    // p0가 group인거고, p0의 children이 그 밑에 UID..

            p0.children.forEach {
                var map = it.value as Map<String, Any>

                val leaderUID = map["leaderUID"].toString()
                if(leaderUID != requestedLeaderUID){ // 내가 클릭한 리더의 방이 아니면.. 돌아가!
                    return@forEach
                }

                Log.i("테스트", map["title"].toString() + "@@@@@@@@@@@@@@@@@@@@@")
                txt_group_title.text = map["title"].toString()
                txt_group_place.text = map["place"].toString()
                txt_group_date.text = map["date"].toString()
                txt_group_people.text = map["people"].toString()
                txt_group_subject.text = map["subject"].toString()
                txt_group_introduce.text = map["introduce"].toString()

                txt_group_introduce_date.text = map["introduce_date"].toString()
                txt_group_introduce_place.text = map["introduce_place"].toString()
                txt_group_introduce_leader.text = map["introduce_leader"].toString()

                //val profile = "https://firebasestorage.googleapis.com/v0/b/anyanglinker.appspot.com/o/profile.png?alt=media&token=a8c36e85-b7c6-495a-b627-866bd9772bb5"
            }
        }
    })*/