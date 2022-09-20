package com.elkenany.views.home.home_sector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.BigRecyclerItemWithDetailsBinding
import com.elkenany.entities.home_data.SectorsGuide

class SectorsGuideAdapter(private val sectorClickListener: ClickListener<SectorsGuide>) :
    ListAdapter<SectorsGuide, SectorGuideViewHolder>(
        SectorGuideAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectorGuideViewHolder {
        return SectorGuideViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SectorGuideViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class SectorGuideViewHolder private constructor(private val binding: BigRecyclerItemWithDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(guide: SectorsGuide, sectorClickListener: ClickListener<SectorsGuide>) {
        binding.data = guide
        binding.url = guide.image
        binding.title = guide.name
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): SectorGuideViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemWithDetailsBinding.inflate(layoutInflater, parent, false)
            return SectorGuideViewHolder(binding)
        }
    }
}


class SectorGuideAdapterDiffUtil : DiffUtil.ItemCallback<SectorsGuide>() {
    override fun areItemsTheSame(oldItem: SectorsGuide, newItem: SectorsGuide): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SectorsGuide, newItem: SectorsGuide): Boolean {
        return oldItem == newItem
    }
}
