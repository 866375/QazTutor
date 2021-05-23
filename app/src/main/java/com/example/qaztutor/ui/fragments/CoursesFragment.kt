package com.example.qaztutor.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qaztutor.adapters.CoursesAdapter
import com.example.qaztutor.databinding.FragmentCoursesBinding
import com.example.qaztutor.models.Course
import com.example.qaztutor.network.RetrofitClient
import com.example.qaztutor.ui.activities.CourseViewActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "CoursesFragment"

class CoursesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mBinding: FragmentCoursesBinding
    private lateinit var mActivity: Activity
    private lateinit var mCoursesAdapter: CoursesAdapter
    private lateinit var courses_list: List<Course>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentCoursesBinding.inflate(inflater, container, false)
        //courses_list = emptyList()
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity!!


        RetrofitClient.instance.getAllCourses().enqueue(object : Callback<List<Course>> {
            override fun onResponse(
                call: Call<List<Course>>,
                response: Response<List<Course>>
            ) {
                setUpCoursesRecyclerView(response.body()!!)
            }

            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                Log.i(TAG, "onFailure: " + t.message)
            }

        })


    }

    fun setUpCoursesRecyclerView(courses: List<Course>) {
        val layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false)
        mCoursesAdapter = CoursesAdapter(courses)
        mBinding.coursesRecyclerView.layoutManager = layoutManager
        mBinding.coursesRecyclerView.setHasFixedSize(true)
        mBinding.coursesRecyclerView.adapter = mCoursesAdapter

        mCoursesAdapter.onItemClick = {
            var intent = Intent(mActivity, CourseViewActivity::class.java)
            intent.putExtra("course_id", it.id)
            startActivity(intent)
        }

    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoursesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}