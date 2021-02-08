package com.example.anyang_linker.fragments.study

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.deadline_list_item.view.*

class DeadlineViewPagerAdapter(private val context : Context) : PagerAdapter() {

    private var layoutInflater : LayoutInflater? = null

    val item = arrayListOf<ExtraStudyItem>(
        ExtraStudyItem("저희와 같이 장학금을 타실분!", R.drawable.profile, "인공지능"),
        ExtraStudyItem("SI랩실에서 튜티를 구합니다.", R.drawable.profile4, "데이터구조"),
        ExtraStudyItem("테스트", R.drawable.profile, "임시"),
        ExtraStudyItem("테스트", R.drawable.profile, "임시")
    )
    /*val Image = arrayOf(
        R.drawable.banner3,
        R.drawable.banner3,
        R.drawable.banner3,
        R.drawable.banner3,
        R.drawable.banner3
    )*/

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ===  `object`
    }

    override fun getCount(): Int {
        return item.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.deadline_list_item, null)
        val txt = v.txt_title_deadline
        val image = v.img_profile_deadline
        val txt2  = v.txt_temp

        image.setImageResource(item[position].res)
        txt.text = item[position].title
        txt2.text = item[position].subject

        val vp = container as ViewPager
        vp.addView(v , 0)

        image.setOnClickListener {
            Toast.makeText(context, position.toString() +"번째 이미지입니다", Toast.LENGTH_SHORT).show()
        }
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }
}