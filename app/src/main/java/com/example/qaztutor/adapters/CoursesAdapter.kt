package com.example.qaztutor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qaztutor.databinding.CoursesRawBinding
import com.example.qaztutor.models.Course

class CoursesAdapter(private val courses: List<Course>) :
    RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {

    var onItemClick: ((Course) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CoursesRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courses.get(position))
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    inner class ViewHolder(private val mBinding: CoursesRawBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(course: Course) {
            mBinding.courseTitleTextView.setText(course.title)
        }

        init {
            mBinding.root.setOnClickListener {
                onItemClick?.invoke(courses[adapterPosition])
            }
        }
    }
}