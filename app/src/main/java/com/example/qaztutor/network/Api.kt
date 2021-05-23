package com.example.qaztutor.network

import com.example.qaztutor.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("courses/")
    fun getAllCourses(): Call<List<Course>>

    //works
    @GET("courses/{id}")
    fun getCourse(@Path("id") id: String): Call<Course>

    @GET("tests/")
    fun getTests(@Query("course_id") course_id: String): Call<List<Task>>

    @GET("test_questions/")
    fun getQuestions(@Query("test_id") test_id: String): Call<List<QuestionData>>

    @GET("lessons/")
    fun getLessons(@Query("course_id") id: String): Call<List<Lesson>>

    @GET("chapters/")
    fun getChapters(@Query("lesson_id") lesson_id: String): Call<List<Chapter>>

}