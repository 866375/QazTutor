package com.example.qaztutor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.qaztutor.R
import com.example.qaztutor.databinding.AnswersRawItemBinding
import com.example.qaztutor.models.Answer

class AnswersAdapter(private val answers: List<Answer>) :
    RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {

    var onItemClick: ((Answer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AnswersRawItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(answers.get(position))
    }

    override fun getItemCount(): Int {
        return answers.size
    }


    inner class ViewHolder(private val mBinding: AnswersRawItemBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(answer: Answer) {
            if (answer.checked) {
                Glide.with(mBinding.root.context).load(R.drawable.selected_img)
                    .into(mBinding.checkedImageView)
            } else {
                Glide.with(mBinding.root.context).load(R.drawable.quiz_pick_background)
                    .into(mBinding.checkedImageView)
            }

            mBinding.answerBodyTextView.setText(answer.answer_body)

        }

        init {
            mBinding.root.setOnClickListener {
                onItemClick?.invoke(answers[adapterPosition])
            }
        }
    }

}