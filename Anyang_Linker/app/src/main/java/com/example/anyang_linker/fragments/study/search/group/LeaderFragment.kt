package com.example.anyang_linker.fragments.study.search.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.fragment_leader.*

class LeaderFragment() : Fragment() {

    var map = StudySearchResultRoomActivity.requestedMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leader, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt_group_introduce_leader.text = map["introduce_leader"]
    }
}
