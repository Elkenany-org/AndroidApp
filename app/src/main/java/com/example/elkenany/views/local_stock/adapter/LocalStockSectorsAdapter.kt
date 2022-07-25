package com.example.elkenany.views.local_stock.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.SmallRecyclerItemBinding
import com.example.elkenany.entities.stock_data.LocalStockSector

class LocalStockSectorsAdapter(private val sectorClickListener: ClickListener<LocalStockSector>) :
    ListAdapter<LocalStockSector, LocalStockSectorViewHolder>(
        LocalStockSectorAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalStockSectorViewHolder {
        return LocalStockSectorViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocalStockSectorViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class LocalStockSectorViewHolder private constructor(private val binding: SmallRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(sector: LocalStockSector, sectorClickListener: ClickListener<LocalStockSector>) {
        binding.data = sector
        binding.name = sector.name
        if (sector.selected.toString() == "1") {
            binding.itemTitle.setBackgroundColor(Color.YELLOW)
        } else if (sector.selected.toString() == "0") {
            binding.itemTitle.setBackgroundColor(Color.WHITE)
        }
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): LocalStockSectorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SmallRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return LocalStockSectorViewHolder(binding)
        }
    }
}


class LocalStockSectorAdapterDiffUtil : DiffUtil.ItemCallback<LocalStockSector>() {
    override fun areItemsTheSame(oldItem: LocalStockSector, newItem: LocalStockSector): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocalStockSector, newItem: LocalStockSector): Boolean {
        return oldItem == newItem
    }
}