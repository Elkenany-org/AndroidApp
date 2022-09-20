package com.elkenany.views.home.home_sector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.SmallRecyclerItemBinding
import com.elkenany.entities.home_data.Sector

class SectorsAdapter(private val sectorClickListener: ClickListener<Sector>) :
    ListAdapter<Sector, SectorViewHolder>(
        SectorAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectorViewHolder {
        return SectorViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SectorViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class SectorViewHolder private constructor(private val binding: SmallRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(sector: Sector, sectorClickListener: ClickListener<Sector>) {
        binding.data = sector
        binding.name = sector.name
        binding.image = ""
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): SectorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SmallRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return SectorViewHolder(binding)
        }
    }
}


class SectorAdapterDiffUtil : DiffUtil.ItemCallback<Sector>() {
    override fun areItemsTheSame(oldItem: Sector, newItem: Sector): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Sector, newItem: Sector): Boolean {
        return oldItem == newItem
    }
}

