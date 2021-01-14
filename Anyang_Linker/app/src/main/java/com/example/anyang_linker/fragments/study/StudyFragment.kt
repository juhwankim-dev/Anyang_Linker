package com.example.anyang_linker.fragments.study

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.anyang_linker.R

class StudyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        /* 뭔지 모르겠지만 Fragment에 Adapter추가하려면 해야하는거 */
        val rootView = inflater.inflate(R.layout.fragment_study, container, false)
        var stRV = rootView.findViewById(R.id.studyRecyclerView) as RecyclerView

        /* 매니저랑 어댑터 추가 */
        stRV.adapter = MyStudyAdapter() // 어댑터 생성
        stRV.layoutManager = LinearLayoutManager(this.context)

        /* Spinner 사용하려면 적어야 하는거 */
        var spinnerArray = arrayListOf<String>("1번", "2번")
        val spinner = rootView.findViewById<Spinner>(R.id.mySpinner)
        spinner?.adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, spinnerArray) as SpinnerAdapter

        spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
                println(type)
            }

        }

        return rootView
    }
}
