package com.example.qaztutor.ui.activities

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.qaztutor.adapters.CourseViewAdapter
import com.example.qaztutor.databinding.ActivityCourseViewBinding
import com.example.qaztutor.models.Lesson
import com.example.qaztutor.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "CourseViewActivity"

class CourseViewActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCourseViewBinding
    private lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCourseViewBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mActivity = this

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        );

        mBinding.backCardView.setOnClickListener { onBackPressed() }

        val course_data = ArrayList<Lesson>()

        val course_id = intent.getStringExtra("course_id")

        GlobalScope.launch(Dispatchers.IO) {
            RetrofitClient.instance.getLessons(course_id!!)
                .enqueue(object : Callback<List<Lesson>> {
                    override fun onResponse(
                        call: Call<List<Lesson>>,
                        response: Response<List<Lesson>>
                    ) {
                        if (response.isSuccessful) {
                            for (lesson: Lesson in response.body()!!) {
                                course_data.add(lesson)
                            }
                            var lesson = Lesson()
                            lesson.title = "Сөз таптары"
                            var lesson1 = Lesson()
                            lesson1.title = "Сөз құрамы"
                            course_data.add(lesson)
                            course_data.add(lesson1)
                            setUpRecyclerView(course_data)
                        } else {
                            Toast.makeText(
                                mActivity,
                                response.message() + "mynda",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            Log.i(
                                TAG,
                                "onResponse: ${response.message()}"
                            )
                        }
                    }

                    override fun onFailure(call: Call<List<Lesson>>, t: Throwable) {
                        Toast.makeText(mActivity, t.message + "here", Toast.LENGTH_SHORT).show()
                        Log.i(TAG, "onResponse: ${t.message}")
                    }

                })
        }


    }

    fun setUpRecyclerView(data: List<Lesson>) {
        val layoutManager = GridLayoutManager(mActivity, 2)
        val mCourseViewAdapter = CourseViewAdapter(data)
        mBinding.courseViewRecyclerView.layoutManager = layoutManager
        mBinding.courseViewRecyclerView.setHasFixedSize(true)
        mBinding.courseViewRecyclerView.adapter = mCourseViewAdapter
    }

}