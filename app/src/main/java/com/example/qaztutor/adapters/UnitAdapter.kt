package com.example.qaztutor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qaztutor.databinding.UnitRawBinding
import com.example.qaztutor.models.Chapter

class UnitAdapter(private val units: List<Chapter>) :
    RecyclerView.Adapter<UnitAdapter.ViewHolder>() {

    var onItemClick: ((Chapter) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UnitRawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(units.get(position))
    }

    override fun getItemCount(): Int {
        return units.size
    }

    inner class ViewHolder(private val mBinding: UnitRawBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        val mActivity = mBinding.root.context

        fun bind(unit: Chapter) {
            mBinding.unitNumberTextView.setText("${adapterPosition+1}")
            mBinding.unitTitleTextView.setText(unit.title)
        }

        init {
            mBinding.root.setOnClickListener {
                units[adapterPosition].id = "${adapterPosition+1}"
                onItemClick?.invoke(units[adapterPosition])
            }
        }

    }


}

