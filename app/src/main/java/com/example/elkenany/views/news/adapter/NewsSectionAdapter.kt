package com.example.elkenany.views.news.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.SmallRecyclerItemBinding
import com.example.elkenany.entities.news_data.NewsSection

class NewsSectionAdapter(private val sectorClickListener: ClickListener<NewsSection>) :
    ListAdapter<NewsSection, NewsSectionViewHolder>(
        NewsSectionAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSectionViewHolder {
        return NewsSectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsSectionViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class NewsSectionViewHolder private constructor(private val binding: SmallRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(section: NewsSection, sectorClickListener: ClickListener<NewsSection>) {
        binding.data = section
        binding.name = section.name
        if (section.selected.toString() == "1") {
            binding.itemTitle.setBackgroundColor(Color.YELLOW)
        } else if (section.selected.toString() == "0") {
            binding.itemTitle.setBackgroundColor(Color.WHITE)
        }
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): NewsSectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SmallRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return NewsSectionViewHolder(binding)
        }
    }
}


class NewsSectionAdapterDiffUtil : DiffUtil.ItemCallback<NewsSection>() {
    override fun areItemsTheSame(oldItem: NewsSection, newItem: NewsSection): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsSection, newItem: NewsSection): Boolean {
        return oldItem == newItem
    }
}