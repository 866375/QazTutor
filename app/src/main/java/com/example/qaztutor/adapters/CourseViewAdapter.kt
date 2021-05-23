package com.example.qaztutor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qaztutor.databinding.CourseViewRawItemBinding
import com.example.qaztutor.models.Course
import com.example.qaztutor.models.Lesson

class CourseViewAdapter(private val lessons: List<Lesson>) :
    RecyclerView.Adapter<CourseViewAdapter.ViewHolder>() {

    var onItemClick: ((Lesson) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CourseViewRawItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lessons.get(position))
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    inner class ViewHolder(private val mBinding: CourseViewRawItemBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(course: Lesson) {
            mBinding.lessonTitleTextView.setText(course.title)
        }

        init {
            mBinding.root.setOnClickListener {
                onItemClick?.invoke(lessons[adapterPosition])
            }
        }
    }
}