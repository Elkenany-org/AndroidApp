package com.elkenany.views.ships.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.SearchResultItemBinding
import com.elkenany.databinding.ShipsDetailsRowItemBinding
import com.elkenany.entities.ships.Ship

class ShipsAdapter(private val clickListener: ClickListener<Ship>) :
    ListAdapter<Ship, ShipViewHolder>(
        ShipAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipViewHolder {
        return ShipViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShipViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ShipViewHolder private constructor(private val binding: ShipsDetailsRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(result: Ship, clickListener: ClickListener<Ship>) {
        binding.data = result
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ShipViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ShipsDetailsRowItemBinding.inflate(layoutInflater, parent, false)
            return ShipViewHolder(binding)
        }
    }
}


class ShipAdapterDiffUtil : DiffUtil.ItemCallback<Ship>() {
    override fun areItemsTheSame(oldItem: Ship, newItem: Ship): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Ship, newItem: Ship): Boolean {
        return oldItem == newItem
    }
}