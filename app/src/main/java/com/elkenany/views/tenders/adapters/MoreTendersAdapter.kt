package com.elkenany.views.tenders.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.NewsCardViewItemBinding
import com.elkenany.entities.tenders.MoreTenders

class MoreTendersAdapter(private val clickListener: ClickListener<MoreTenders>) :
    ListAdapter<MoreTenders, MoreTendersViewHolder>(
        MoreTendersAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreTendersViewHolder {
        return MoreTendersViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MoreTendersViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class MoreTendersViewHolder private constructor(private val binding: NewsCardViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(news: MoreTenders, clickListener: ClickListener<MoreTenders>) {
        binding.data = news
        binding.url = news.image
        binding.title = news.title
        binding.createdAt = news.createdAt
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): MoreTendersViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NewsCardViewItemBinding.inflate(layoutInflater, parent, false)
            return MoreTendersViewHolder(binding)
        }
    }
}


class MoreTendersAdapterDiffUtil : DiffUtil.ItemCallback<MoreTenders>() {
    override fun areItemsTheSame(oldItem: MoreTenders, newItem: MoreTenders): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoreTenders, newItem: MoreTenders): Boolean {
        return oldItem == newItem
    }


}