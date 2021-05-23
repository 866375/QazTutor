package com.example.qaztutor.ui.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qaztutor.adapters.LessonsAdapter
import com.example.qaztutor.adapters.TasksAdapter
import com.example.qaztutor.databinding.FragmentHomeBinding
import com.example.qaztutor.models.Lesson
import com.example.qaztutor.models.Task
import com.example.qaztutor.network.RetrofitClient
import com.example.qaztutor.ui.activities.LessonsActivity
import com.example.qaztutor.ui.activities.TaskActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "HomeFragment"
private const val course_id = "1"
private const val QUIZ_RESULT_CODE = 101

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mActivity: Activity
    private lateinit var mLessonsAdapter: LessonsAdapter
    private lateinit var mUncompletedTasksAdapter: TasksAdapter
    private lateinit var quiz_result: String


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

        //test data
        val test_lessons_data = ArrayList<Lesson>()

        GlobalScope.launch(Dispatchers.IO) {
            RetrofitClient.instance.getLessons(course_id).enqueue(object : Callback<List<Lesson>> {
                override fun onResponse(
                    call: Call<List<Lesson>>,
                    response: Response<List<Lesson>>
                ) {
                    if (response.isSuccessful) {
                        for (lesson: Lesson in response.body()!!) {
                            test_lessons_data.add(lesson)
                        }
                        var lesson = Lesson()
                        lesson.title = "Сөз таптары"
                        var lesson1 = Lesson()
                        lesson1.title = "Сөз құрамы"
                        test_lessons_data.add(lesson)
                        test_lessons_data.add(lesson1)
                        setUpLessonsRecyclerView(test_lessons_data)
                    } else {
                        Toast.makeText(mActivity, response.message() + "mynda", Toast.LENGTH_SHORT)
                            .show()
                        Log.i(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<Lesson>>, t: Throwable) {
                    Toast.makeText(mActivity, t.message + "here", Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "onResponse: ${t.message}")
                }

            })
        }


        GlobalScope.launch(Dispatchers.IO) {
            RetrofitClient.instance.getTests(course_id).enqueue(object : Callback<List<Task>> {
                override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                    if (response.isSuccessful) {
                        var quizes = response.body()
                        for (quiz: Task in quizes!!) {
                            test_task_data.add(quiz)
                        }
                        setUpUncompletedTasksRecyclerView(test_task_data)
                    } else {
                        Toast.makeText(mActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                    Toast.makeText(mActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }


    }


    fun setUpUncompletedTasksRecyclerView(task: List<Task>) {
        val layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false)
        mUncompletedTasksAdapter = TasksAdapter(task)
        mBinding.uncompletedTasksRecyclerView.layoutManager = layoutManager
        mBinding.uncompletedTasksRecyclerView.setHasFixedSize(true)
        mBinding.uncompletedTasksRecyclerView.adapter = mUncompletedTasksAdapter

        mUncompletedTasksAdapter.onItemClick = {
            var intent = Intent(mActivity, TaskActivity::class.java)
            intent.putExtra("task", it)
            startActivityForResult(intent, QUIZ_RESULT_CODE)
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
            intent.putExtra("lesson_id", it.id)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == QUIZ_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    //result of quiz
                    quiz_result = data.extras?.getString("quiz_result").toString()
                    //Toast.makeText(mActivity, quiz_result, Toast.LENGTH_SHORT).show()
                }
            }
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