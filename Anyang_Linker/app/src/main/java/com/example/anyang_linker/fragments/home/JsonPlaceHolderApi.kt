package com.example.anyang_linker.fragments.home

import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/* 뭔지 모르겠지만 걍 똑같이 만들면 되는 듯 하다.*/
interface JsonPlaceHolderApi {

    @FormUrlEncoded
    @POST("boardList.do")
    fun boardListPost(@FieldMap fields: MutableMap<String, String>): Call<Result?>?
    // (= Call<Result> createPost(@FieldMap Map<String, String> fields); )
}