package com.example.anyang_linker.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2

import com.example.anyang_linker.R
import com.example.anyang_linker.SplashActivity
import com.example.anyang_linker.fragments.home.memo.AppDatabase
import com.example.anyang_linker.fragments.home.memo.TodoActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(){
    lateinit var todoAdapter : ThisWeekAdapter

    //뷰페이저 2
    lateinit var viewPagerAdapter: BannerViewPagerAdapter
    lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        /* ------------------------------ 더보기 클릭했을 때 -------------------------------*/
        view.noticeLinearLayout.setOnClickListener { view ->
            val goAllnoticesActivity = Intent(activity, AllNoticesActivity::class.java)
            startActivity(goAllnoticesActivity)
        }
        /* ------------------------------ 더보기 클릭했을 때 -------------------------------*/

        /* ------------------------------ DB 읽어오기 -------------------------------*/
        val db = Room.databaseBuilder(
            context!!,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries()
            .build()
        /* ------------------------------ DB 읽어오기 -------------------------------*/

        /* ------------------------------ Adapter랑 Manager -------------------------------*/
        /* 뭔지 모르겠지만 Fragment에 Adapter추가하려면 해야하는거 */
        // val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val thisWeekRecyclerView = view.findViewById(R.id.thisWeekRecyclerView) as RecyclerView

        /* 매니저랑 어댑터 추가 */
        var todoList = db.todoDao().getAll()
        todoAdapter = ThisWeekAdapter(todoList)
        thisWeekRecyclerView.adapter = todoAdapter // 어댑터 생성
        thisWeekRecyclerView.layoutManager = LinearLayoutManager(activity)
        /* ------------------------------ Adapter랑 Manager -------------------------------*/

        return view
    }

    override fun onStart() {
        super.onStart()

        /* ------------------------------ 메인 공지 -------------------------------*/
        var str = SplashActivity.mainNotice

        /* [ACE+] 지우고 문장이 너무 길면 뒤에 ... 으로 처리 */
        if(str.length > 30) str = str.slice(7..32) + "..."
        txtMainNotice.text = str
        /* ------------------------------ 메인 공지 -------------------------------*/

        btn_editMemo.setOnClickListener {
            val goMemoEditActivity = Intent(context, TodoActivity::class.java)
            startActivityForResult(goMemoEditActivity, 0)
        }

        btn_submitReport.setOnClickListener {
            var goReport = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.kr/drive/apps.html"))
            startActivity(goReport);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            1 -> {
                /* ------------------------------ DB 읽어오기 -------------------------------*/
                val db = Room.databaseBuilder(
                    context!!,
                    AppDatabase::class.java, "database-name"
                ).allowMainThreadQueries()
                    .build()
                /* ------------------------------ DB 읽어오기 -------------------------------*/

                /* ------------------------------ 메모 불러오기 -------------------------------*/
                // 메모 불러오기
                val callRunnable = Runnable {
                    var todoList = db.todoDao().getAll()
                    todoAdapter = ThisWeekAdapter(todoList)

                    thisWeekRecyclerView.adapter = todoAdapter
                    thisWeekRecyclerView.layoutManager = LinearLayoutManager(activity)
                    thisWeekRecyclerView.setHasFixedSize(true)
                }
                /* ------------------------------ 메모 불러오기 -------------------------------*/

                val callThread = Thread(callRunnable)
                callThread.start()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /* ------------------------------ 배너 -------------------------------*/
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.setBannerItems(
            listOf(
                BannerItem(R.drawable.banner1),
                BannerItem(R.drawable.banner2),
                BannerItem(R.drawable.banner3)
            )
        )
        initViewPager2(txt_curPage)
        subscribeObservers()
        /* ------------------------------ 배너 -------------------------------*/
    }

    /* ------------------------------ 배너 -------------------------------*/
    private fun initViewPager2(textview: TextView) {
        viewPager2.apply {
            viewPagerAdapter = BannerViewPagerAdapter()
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    textview.text = "${position+1}"
                }
            })
        }
    }

    private fun subscribeObservers() {
        viewModel.bannerItemList.observe(this, Observer { bannerItemList ->
            viewPagerAdapter.submitList(bannerItemList)
        })
    }
    /* ------------------------------ 배너 -------------------------------*/
}



/* ------------------------------ 플로팅 액션 버튼 -------------------------------*/
/*view.fab.setOnClickListener { view ->
    var goTodoActivity = Intent(activity, TodoActivity::class.java)
    startActivityForResult(goTodoActivity, 1)
}*/
/* ------------------------------ 플로팅 액션 버튼 -------------------------------*/