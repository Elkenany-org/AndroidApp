package com.elkenany.views.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.SectorItemBinding
import com.elkenany.entities.news_data.NewsSection

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

class NewsSectionViewHolder private constructor(private val binding: SectorItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(section: NewsSection, sectorClickListener: ClickListener<NewsSection>) {
        binding.data = section
        binding.name = section.name
        val selected = section.selected.toString()
        if (selected == "1") {
            binding.indicator.visibility = View.VISIBLE
            binding.indicator.setCardBackgroundColor(binding.root.context.getColor(R.color.orange))
        } else{
            binding.indicator.visibility = View.GONE
        }
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): NewsSectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SectorItemBinding.inflate(layoutInflater, parent, false)
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