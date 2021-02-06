package com.example.anyang_linker.fragments.study.search.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.anyang_linker.R
import com.example.anyang_linker.fragments.study.search.group.StudySearchResultRoomActivity.Companion.requestedMap
import kotlinx.android.synthetic.main.fragment_group.*

class GroupFragment() : Fragment() {

    var map = requestedMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt_group_place.text = map["place"]
        txt_group_date.text = map["date"]
        txt_group_people.text = map["people"] + "명"
        txt_group_subject.text = map["subject"]
        txt_group_introduce.text = map["introduce"]
    }
}
