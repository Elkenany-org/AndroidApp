package com.example.elkenany.views.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.StoreCardViewItemBinding
import com.example.elkenany.entities.store.AdsStoreDaum

class AdsStoreAdapter(private val sectorClickListener: ClickListener<AdsStoreDaum>) :
    ListAdapter<AdsStoreDaum, AdsStoreDaumViewHolder>(
        AdsStoreDaumAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsStoreDaumViewHolder {
        return AdsStoreDaumViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AdsStoreDaumViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class AdsStoreDaumViewHolder private constructor(private val binding: StoreCardViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(ads: AdsStoreDaum, sectorClickListener: ClickListener<AdsStoreDaum>) {
        binding.data = ads
        binding.title = ads.title
        binding.createdAt = ads.createdAt
        binding.location = ads.address
        binding.url = ads.image
        binding.price = ads.salary.toString()
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): AdsStoreDaumViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = StoreCardViewItemBinding.inflate(layoutInflater, parent, false)
            return AdsStoreDaumViewHolder(binding)
        }
    }
}


class AdsStoreDaumAdapterDiffUtil : DiffUtil.ItemCallback<AdsStoreDaum>() {
    override fun areItemsTheSame(oldItem: AdsStoreDaum, newItem: AdsStoreDaum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AdsStoreDaum, newItem: AdsStoreDaum): Boolean {
        return oldItem == newItem
    }
}