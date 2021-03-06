package com.example.anyang_linker.fragments.study

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.toeic_list_item.view.*

class ToeicViewPagerAdapter(private val context : Context) : PagerAdapter() {

    private var layoutInflater : LayoutInflater? = null

    val item = arrayListOf<ExtraStudyItem>(
        ExtraStudyItem("이렇게도 할 수 있으려나요!", R.drawable.profile2,"임시"),
        ExtraStudyItem("테스트", R.drawable.profile3,"임시"),
        ExtraStudyItem("테스트", R.drawable.profile,"임시"),
        ExtraStudyItem("테스트", R.drawable.profile,"임시")
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ===  `object`
    }

    override fun getCount(): Int {
        return item.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.toeic_list_item, null)
        val txt = v.txt_title_toeic
        val image = v.img_profile_toeic

        image.setImageResource(item[position].res)
        txt.text = item[position].title

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