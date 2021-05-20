package com.example.qaztutor.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qaztutor.adapters.LessonsAdapter
import com.example.qaztutor.adapters.TasksAdapter
import com.example.qaztutor.databinding.FragmentHomeBinding
import com.example.qaztutor.models.Course
import com.example.qaztutor.models.Lesson
import com.example.qaztutor.models.Task
import com.example.qaztutor.network.RetrofitClient
import com.example.qaztutor.ui.activities.LessonsActivity
import com.example.qaztutor.ui.activities.TaskActivity
import com.example.qaztutor.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mActivity: Activity
    private lateinit var mLessonsAdapter: LessonsAdapter
    private lateinit var mUncompletedTasksAdapter: TasksAdapter

    override

    fun onCreate(savedInstanceState: Bundle?) {
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
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity!!

        //test data
        val test_task_data = ArrayList<Task>()
        for (i: Int in 1..10) {
            var task = Task()
            if (i % 2 == 0) {
                task.type = Constants.listening
            } else {
                task.type = Constants.grammar
            }
            task.title = "Task $i"
            task.description = "Listen and answer"
            task.completed = false
            test_task_data.add(task)
        }

        setUpUncompletedTasksRecyclerView(test_task_data)

        //test data
        val test_lessons_data = ArrayList<Lesson>()
        for (i: Int in 1..6) {
            var lesson = Lesson()
            lesson.type = Constants.listening
            lesson.completed = false
            lesson.title = "Listening"
            test_lessons_data.add(lesson)
        }

        setUpLessonsRecyclerView(test_lessons_data)


        RetrofitClient.instance.getAllCourses().enqueue(object : Callback<List<Course>> {
            override fun onResponse(
                call: Call<List<Course>>,
                response: Response<List<Course>>
            ) {

            }

            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                Log.i(TAG, "onFailure: " + t.message)
            }

        })


    }


    fun setUpUncompletedTasksRecyclerView(task: List<Task>) {
        val layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false)
        mUncompletedTasksAdapter = TasksAdapter(task)
        mBinding.uncompletedTasksRecyclerView.layoutManager = layoutManager
        mBinding.uncompletedTasksRecyclerView.setHasFixedSize(true)
        mBinding.uncompletedTasksRecyclerView.adapter = mUncompletedTasksAdapter

        mUncompletedTasksAdapter.onItemClick = {
            var intent = Intent(mActivity, TaskActivity::class.java)
            //intent.putExtra("task", it)
            startActivity(intent)
        }
    }

    private fun setUpLessonsRecyclerView(lessons: ArrayList<Lesson>) {
        val layoutManager = GridLayoutManager(mActivity, 2)
        mLessonsAdapter = LessonsAdapter(lessons)
        mBinding.lessonsRecyclerView.layoutManager = layoutManager
        mBinding.lessonsRecyclerView.setHasFixedSize(true)
        mBinding.lessonsRecyclerView.adapter = mLessonsAdapter

        mLessonsAdapter.onItemClick = {
            var intent = Intent(mActivity, LessonsActivity::class.java)
            //intent.putExtra("lesson", it)
            startActivity(intent)
        }
    }


    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}