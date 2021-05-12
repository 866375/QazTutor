package com.example.qaztutor.ui.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.qaztutor.adapters.CourseViewAdapter
import com.example.qaztutor.databinding.ActivityCourseViewBinding
import com.example.qaztutor.models.Course

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

        val courses_test_data = ArrayList<Course>()
        for (i: Int in 1..6) {
            var course = Course()
            courses_test_data.add(course)
        }

        val layoutManager = GridLayoutManager(mActivity, 2)
        val mCourseViewAdapter = CourseViewAdapter(courses_test_data)
        mBinding.courseViewRecyclerView.layoutManager = layoutManager
        mBinding.courseViewRecyclerView.setHasFixedSize(true)
        mBinding.courseViewRecyclerView.adapter = mCourseViewAdapter

    }
}