package com.elkenany.views.home.home_sector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.BigRecyclerItemWithDetailsBinding
import com.elkenany.entities.home_data.SectorsStore

class SectorsStoreAdapter(private val recommendationClickListener: ClickListener<SectorsStore>) :
    ListAdapter<SectorsStore, SectorStoreViewHolder>(
        SectorStoreAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SectorStoreViewHolder {
        return SectorStoreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SectorStoreViewHolder, position: Int) {
        holder.bind(getItem(position)!!, recommendationClickListener)
    }


}

class SectorStoreViewHolder private constructor(private val binding: BigRecyclerItemWithDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        store: SectorsStore,
        recommendationClickListener: ClickListener<SectorsStore>,
    ) {
        binding.data = store
        binding.url = store.image
        binding.title = store.name
        binding.clickListener = recommendationClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): SectorStoreViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemWithDetailsBinding.inflate(layoutInflater, parent, false)
            return SectorStoreViewHolder(binding)
        }
    }
}


class SectorStoreAdapterDiffUtil : DiffUtil.ItemCallback<SectorsStore>() {
    override fun areItemsTheSame(
        oldItem: SectorsStore,
        newItem: SectorsStore,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SectorsStore,
        newItem: SectorsStore,
    ): Boolean {
        return oldItem == newItem
    }
}