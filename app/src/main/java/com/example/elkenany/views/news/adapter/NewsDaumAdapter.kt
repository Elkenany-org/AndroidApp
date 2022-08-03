package com.example.elkenany.views.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.NewsCardViewItemBinding
import com.example.elkenany.entities.news_data.NewsDaum

class NewsDaumAdapter(private val sectorClickListener: ClickListener<NewsDaum>) :
    ListAdapter<NewsDaum, NewsDaumViewHolder>(
        NewsDaumAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsDaumViewHolder {
        return NewsDaumViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsDaumViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class NewsDaumViewHolder private constructor(private val binding: NewsCardViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(news: NewsDaum, sectorClickListener: ClickListener<NewsDaum>) {
        binding.data = news
        binding.url = news.image
        binding.title = news.title
        binding.createdAt= news.createdAt
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): NewsDaumViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NewsCardViewItemBinding.inflate(layoutInflater, parent, false)
            return NewsDaumViewHolder(binding)
        }
    }
}


class NewsDaumAdapterDiffUtil : DiffUtil.ItemCallback<NewsDaum>() {
    override fun areItemsTheSame(oldItem: NewsDaum, newItem: NewsDaum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsDaum, newItem: NewsDaum): Boolean {
        return oldItem == newItem
    }

}