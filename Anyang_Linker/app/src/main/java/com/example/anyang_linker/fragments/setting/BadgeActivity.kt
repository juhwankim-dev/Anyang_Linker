package com.example.anyang_linker.fragments.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.activity_badge.*

class BadgeActivity : AppCompatActivity() {

    private var gridView:GridView ? = null
    private var arrayList:ArrayList<BadgeItem> ? = null
    private var badgeAdapter:BadgeAdapter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge)

        //gridView = findViewById(R.id.gridView_badge)
        arrayList = ArrayList()
        arrayList = setDataList()
        badgeAdapter = BadgeAdapter(this, arrayList!!)
        gridView_badge?.adapter = badgeAdapter

    }

    private fun setDataList(): ArrayList<BadgeItem>{
        var arrayList: ArrayList<BadgeItem> = ArrayList()

        arrayList.add(BadgeItem(R.drawable.badgestart, "스터디로 첫걸음"))
        arrayList.add(BadgeItem(R.drawable.badgeteam, "우수 팀 시상"))
        arrayList.add(BadgeItem(R.drawable.badgeleader, "우수 리더 선정"))
        arrayList.add(BadgeItem(R.drawable.badge2time, "스터디 2회 참여"))
        arrayList.add(BadgeItem(R.drawable.badge5time, "스터디 5회 참여"))
        arrayList.add(BadgeItem(R.drawable.badgereport, "개인 보고서 우수"))

        return arrayList
    }
}
