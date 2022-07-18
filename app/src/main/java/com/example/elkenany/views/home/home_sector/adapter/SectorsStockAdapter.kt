package com.example.elkenany.views.home.home_sector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.BigRecyclerItemBinding
import com.example.elkenany.entities.home_data.SectorsStock

class SectorsStockAdapter(private val partnerClickListener: ClickListener<SectorsStock>) :
    ListAdapter<SectorsStock, SectorsStockViewHolder>(
        SectorsStockDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectorsStockViewHolder {
        return SectorsStockViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SectorsStockViewHolder, position: Int) {
        holder.bind(getItem(position)!!, partnerClickListener)
    }

}

class SectorsStockViewHolder private constructor(private val binding: BigRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(stock: SectorsStock, partnerClickListener: ClickListener<SectorsStock>) {
        binding.data = stock
        binding.url = stock.image
        binding.clickListener = partnerClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): SectorsStockViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return SectorsStockViewHolder(binding)
        }
    }
}


class SectorsStockDiffUtil : DiffUtil.ItemCallback<SectorsStock>() {
    override fun areItemsTheSame(oldItem: SectorsStock, newItem: SectorsStock): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SectorsStock, newItem: SectorsStock): Boolean {
        return oldItem == newItem
    }
}
