package com.elkenany.views.ships.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.ShipsStatisticsCardItemBinding
import com.elkenany.entities.ships.ShipDaum

class StatisticsAdapter(private val clickListener: ClickListener<ShipDaum>) :
    ListAdapter<ShipDaum, ShipDaumViewHolder>(
        ShipDaumDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipDaumViewHolder {
        return ShipDaumViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShipDaumViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ShipDaumViewHolder private constructor(private val binding: ShipsStatisticsCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(result: ShipDaum, clickListener: ClickListener<ShipDaum>) {
        binding.data = result
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ShipDaumViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ShipsStatisticsCardItemBinding.inflate(layoutInflater, parent, false)
            return ShipDaumViewHolder(binding)
        }
    }
}


class ShipDaumDiffUtil : DiffUtil.ItemCallback<ShipDaum>() {
    override fun areItemsTheSame(oldItem: ShipDaum, newItem: ShipDaum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShipDaum, newItem: ShipDaum): Boolean {
        return oldItem == newItem
    }
}