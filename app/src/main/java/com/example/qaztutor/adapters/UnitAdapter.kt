package com.example.qaztutor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qaztutor.databinding.UnitRawBinding
import com.example.qaztutor.models.TestUnit

class UnitAdapter(private val units: List<TestUnit>) :
    RecyclerView.Adapter<UnitAdapter.ViewHolder>() {

    var onItemClick: ((TestUnit) -> Unit)? = null


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

        fun bind(unit: TestUnit) {
            mBinding.unitNumberTextView.setText(unit.id)
            mBinding.unitTitleTextView.setText(unit.title)
        }

        init {
            mBinding.root.setOnClickListener {
                onItemClick?.invoke(units[adapterPosition])
            }
        }

    }


}