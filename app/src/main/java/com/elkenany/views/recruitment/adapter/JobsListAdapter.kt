package com.elkenany.views.recruitment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.JobCardItemBinding
import com.elkenany.entities.recruitment.JobDaum

class JobsListAdapter(
    private val clickListener: ClickListener<JobDaum>,
    private val bookMarkListener: ClickListener<JobDaum>,
) :
    ListAdapter<JobDaum, JobsDaumAdapterViewHolder>(
        JobsDaumDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsDaumAdapterViewHolder {
        return JobsDaumAdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: JobsDaumAdapterViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
        holder.itemView.findViewById<ImageView>(R.id.book_mark_btn).setOnClickListener {
            bookMarkListener.onClick(getItem(position))
            it.setBackgroundResource(R.drawable.ic_book_mark_fill)
        }
    }

}

class JobsDaumAdapterViewHolder private constructor(private val binding: JobCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: JobDaum,
        clickListener: ClickListener<JobDaum>,
    ) {
        binding.data = data
        binding.clickListener = clickListener
        binding.local = "L.E"
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): JobsDaumAdapterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = JobCardItemBinding.inflate(layoutInflater, parent, false)
            return JobsDaumAdapterViewHolder(binding)
        }
    }
}


class JobsDaumDiffUtil : DiffUtil.ItemCallback<JobDaum>() {
    override fun areItemsTheSame(oldItem: JobDaum, newItem: JobDaum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: JobDaum, newItem: JobDaum): Boolean {
        return oldItem == newItem
    }
}