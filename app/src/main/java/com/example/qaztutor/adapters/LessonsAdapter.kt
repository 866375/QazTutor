package com.example.qaztutor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qaztutor.databinding.LessonsRawBinding
import com.example.qaztutor.models.Lesson

class LessonsAdapter(private val lessons: List<Lesson>) :
    RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {

    var onItemClick: ((Lesson) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LessonsRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lessons.get(position))
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    inner class ViewHolder(private val mBinding: LessonsRawBinding, private val mContext: Context) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(lesson: Lesson) {
            mBinding.lessonTitleTextView.text = lesson.title

            if (lesson.completed) {
                mBinding.completedTextView.setText("Completed")
            } else {
                mBinding.completedTextView.setText("In Progress")
            }
        }

        init {
            mBinding.root.setOnClickListener {
                onItemClick?.invoke(lessons[adapterPosition])
            }
        }

    }
}