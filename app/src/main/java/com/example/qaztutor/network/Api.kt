package com.example.qaztutor.network

import com.example.qaztutor.models.Course
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("courses/")
    fun getAllCourses(): Call<List<Course>>

//    @GET("states/daily.json")
//    fun getStatesData(): Call<List<TestDataModelItem>>

}