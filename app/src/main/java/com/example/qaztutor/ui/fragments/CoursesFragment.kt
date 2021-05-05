package com.example.qaztutor.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qaztutor.adapters.CoursesAdapter
import com.example.qaztutor.databinding.FragmentCoursesBinding
import com.example.qaztutor.models.Course

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CoursesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mBinding: FragmentCoursesBinding
    private lateinit var mActivity: Activity

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
        return mBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CoursesFragment.
         */
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity!!


        val courses_test_data = ArrayList<Course>()
        for (i: Int in 1..6) {
            var course = Course()
            courses_test_data.add(course)
        }

        setUpCoursesRecyclerView(courses_test_data)


    }

    fun setUpCoursesRecyclerView(courses: List<Course>) {
        val layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false)
        var coursesAdapter = CoursesAdapter(courses)
        mBinding.coursesRecyclerView.layoutManager = layoutManager
        mBinding.coursesRecyclerView.setHasFixedSize(true)
        mBinding.coursesRecyclerView.adapter = coursesAdapter
    }
}