package com.elkenany.views.home.home_sector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.BigRecyclerItemBinding
import com.elkenany.entities.home_data.SectorsNews

class SectorsNewAdapter(private val recommendationClickListener: ClickListener<SectorsNews>) :
    ListAdapter<SectorsNews, SectorsNewsViewHolder>(
        SectorsNewsAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SectorsNewsViewHolder {
        return SectorsNewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SectorsNewsViewHolder, position: Int) {
        holder.bind(getItem(position)!!, recommendationClickListener)
    }


}

class SectorsNewsViewHolder private constructor(private val binding: BigRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        news: SectorsNews,
        recommendationClickListener: ClickListener<SectorsNews>,
    ) {
        binding.data = news
        binding.url = news.image
        binding.clickListener = recommendationClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): SectorsNewsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return SectorsNewsViewHolder(binding)
        }
    }
}


class SectorsNewsAdapterDiffUtil : DiffUtil.ItemCallback<SectorsNews>() {
    override fun areItemsTheSame(
        oldItem: SectorsNews,
        newItem: SectorsNews,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SectorsNews,
        newItem: SectorsNews,
    ): Boolean {
        return oldItem == newItem
    }
}