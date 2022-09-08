package com.example.elkenany.views.store.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.SectorItemBinding
import com.example.elkenany.entities.stock_data.LocalStockSector

class MyAdsSectorsAdapter(
    private val sectorClickListener: ClickListener<LocalStockSector>,
) :
    ListAdapter<LocalStockSector, MyAdsSectorsViewHolder>(
        MyAdsSectorsAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdsSectorsViewHolder {
        return MyAdsSectorsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyAdsSectorsViewHolder, position: Int) {
        holder.itemView.findViewById<CardView>(R.id.indicator)
            .startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,
                com.bumptech.glide.R.anim.abc_popup_enter))
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class MyAdsSectorsViewHolder private constructor(private val binding: SectorItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        sector: LocalStockSector,
        sectorClickListener: ClickListener<LocalStockSector>,
    ) {
        binding.data = sector
        binding.name = sector.name
        if (sector.selected.toString() == "1") {
            binding.indicator.apply {
                visibility = View.VISIBLE
                setCardBackgroundColor(binding.root.context.getColor(R.color.orange))
                animate().apply {
                    duration = 250
                    rotationYBy(360f)
                }
            }

        } else if (sector.selected.toString() == "0") {
            binding.indicator.visibility = View.GONE
        }
        binding.clickListener = sectorClickListener

        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): MyAdsSectorsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SectorItemBinding.inflate(layoutInflater, parent, false)
            return MyAdsSectorsViewHolder(binding)
        }
    }
}


class MyAdsSectorsAdapterDiffUtil : DiffUtil.ItemCallback<LocalStockSector>() {
    override fun areItemsTheSame(oldItem: LocalStockSector, newItem: LocalStockSector): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocalStockSector, newItem: LocalStockSector): Boolean {
        return oldItem == newItem
    }
}