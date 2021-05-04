package com.example.qaztutor.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qaztutor.adapters.LessonsAdapter
import com.example.qaztutor.adapters.UncompletedTasksAdapter
import com.example.qaztutor.databinding.FragmentHomeBinding
import com.example.qaztutor.models.Lesson
import com.example.qaztutor.models.Task
import com.example.qaztutor.util.Constants

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mActivity: Activity

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

        val test_lessons_data = ArrayList<Lesson>()
        for (i: Int in 1..6) {
            var lesson = Lesson()
            lesson.type = Constants.listening
            lesson.completed = false
            lesson.title = "Listening"
            test_lessons_data.add(lesson)
        }

        setUpLessonsRecyclerView(test_lessons_data)

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
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

    fun setUpUncompletedTasksRecyclerView(task: List<Task>) {
        val layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false)
        var mUncompletedTasksAdapter = UncompletedTasksAdapter(task)
        mBinding.uncompletedTasksRecyclerView.layoutManager = layoutManager
        mBinding.uncompletedTasksRecyclerView.setHasFixedSize(true)
        mBinding.uncompletedTasksRecyclerView.adapter = mUncompletedTasksAdapter
    }

    private fun setUpLessonsRecyclerView(lessons: ArrayList<Lesson>) {
        val layoutManager = GridLayoutManager(mActivity, 2)
        val mLessonsAdapter = LessonsAdapter(lessons)
        mBinding.lessonsRecyclerView.layoutManager = layoutManager
        mBinding.lessonsRecyclerView.setHasFixedSize(true)
        mBinding.lessonsRecyclerView.adapter = mLessonsAdapter
    }

}