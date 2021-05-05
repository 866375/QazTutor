package com.example.qaztutor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.qaztutor.R
import com.example.qaztutor.databinding.LessonsRawBinding
import com.example.qaztutor.models.Lesson
import com.example.qaztutor.util.Constants

class LessonsAdapter(private val lessons: List<Lesson>) :
    RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {


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

    class ViewHolder(private val mBinding: LessonsRawBinding, private val mContext: Context) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(lesson: Lesson) {
//            if (lesson.type.equals(Constants.listening)) {
//                Glide.with(mContext).load(R.drawable.listening_lesson_img)
//                    .into(mBinding.lessonImageView)
//            } else {
//                // other conditions
//            }

            if (lesson.completed) {
                mBinding.completedTextView.setText("Completed")
            } else {
                mBinding.completedTextView.setText("In Progress")
            }

            mBinding.lessonTitleTextView.setText("Listening")
        }

    }
}