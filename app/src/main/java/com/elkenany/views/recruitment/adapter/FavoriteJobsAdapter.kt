package com.elkenany.views.recruitment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.FavoriteJobCardItemBinding
import com.elkenany.entities.recruitment.FavoriteJob


class FavoriteJobsAdapter(
    private val clickListener: ClickListener<FavoriteJob>,
    private val bookMartIt: ClickListener<FavoriteJob>,
) :
    ListAdapter<FavoriteJob, FavoriteJobsAdapterViewHolder>(
        FavoriteJobsAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoriteJobsAdapterViewHolder {
        return FavoriteJobsAdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteJobsAdapterViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener, bookMartIt)
    }

}

class FavoriteJobsAdapterViewHolder private constructor(private val binding: FavoriteJobCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: FavoriteJob,
        clickListener: ClickListener<FavoriteJob>,
        bookMartIt: ClickListener<FavoriteJob>,
    ) {
        binding.data = data
        binding.clickListener = clickListener
        binding.local = "L.E"
        binding.bookMarkIt = bookMartIt
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): FavoriteJobsAdapterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FavoriteJobCardItemBinding.inflate(layoutInflater, parent, false)
            return FavoriteJobsAdapterViewHolder(binding)
        }
    }
}


class FavoriteJobsAdapterDiffUtil : DiffUtil.ItemCallback<FavoriteJob>() {
    override fun areItemsTheSame(oldItem: FavoriteJob, newItem: FavoriteJob): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteJob, newItem: FavoriteJob): Boolean {
        return oldItem == newItem
    }
}