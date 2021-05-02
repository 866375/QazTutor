package com.example.qaztutor.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qaztutor.R
import com.example.qaztutor.databinding.UncompletedTasksRawBinding
import com.example.qaztutor.models.Task
import com.example.qaztutor.util.Constants


class UncompletedTasksAdapter(private val tasks: List<Task>) :
    RecyclerView.Adapter<UncompletedTasksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding =
            UncompletedTasksRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks.get(position))
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class ViewHolder(private val mBinding: UncompletedTasksRawBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(task: Task) {
            if (!task.completed) {
                mBinding.taskTitleTextView.setText(task.title)
                mBinding.taskDescriptionTextView.setText(task.description)
//                if (task.type.equals(Constants.listening)) {
//                    mBinding.cardView.setBackgroundResource(R.color.listening_background_color)
//                } else if (task.type.equals(Constants.grammar)) {
//                    mBinding.cardView.setBackgroundResource(R.color.grammar_background_color)
//                }
            }
        }

    }

}