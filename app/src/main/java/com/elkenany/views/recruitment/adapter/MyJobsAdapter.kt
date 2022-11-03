package com.elkenany.views.recruitment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.MyJobCardItemBinding
import com.elkenany.entities.recruitment.MyJobDaum

class MyJobsAdapter(private val clickListener: ClickListener<MyJobDaum>) :
    ListAdapter<MyJobDaum, MyJobsAdapterViewHolder>(
        MyJobsAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyJobsAdapterViewHolder {
        return MyJobsAdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyJobsAdapterViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class MyJobsAdapterViewHolder private constructor(private val binding: MyJobCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bind(data: MyJobDaum, clickListener: ClickListener<MyJobDaum>) {
        binding.data = data
        binding.clickListener = clickListener
        binding.status = data.applicationStatus
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): MyJobsAdapterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = MyJobCardItemBinding.inflate(layoutInflater, parent, false)
            return MyJobsAdapterViewHolder(binding)
        }
    }
}


class MyJobsAdapterDiffUtil : DiffUtil.ItemCallback<MyJobDaum>() {
    override fun areItemsTheSame(oldItem: MyJobDaum, newItem: MyJobDaum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MyJobDaum, newItem: MyJobDaum): Boolean {
        return oldItem == newItem
    }
}