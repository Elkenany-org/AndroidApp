package com.elkenany.views.guide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.SmallRecyclerItemBinding
import com.elkenany.entities.guide.Gallary

class GalleryAdapter(private val sectorClickListener: ClickListener<Gallary>) :
    ListAdapter<Gallary, GallaryViewHolder>(
        GallaryAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GallaryViewHolder {
        return GallaryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GallaryViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class GallaryViewHolder private constructor(private val binding: SmallRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        company: Gallary,
        sectorClickListener: ClickListener<Gallary>,
    ) {
        binding.data = company
        binding.image = company.image
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): GallaryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SmallRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return GallaryViewHolder(binding)
        }
    }
}


class GallaryAdapterDiffUtil : DiffUtil.ItemCallback<Gallary>() {
    override fun areItemsTheSame(
        oldItem: Gallary,
        newItem: Gallary,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Gallary,
        newItem: Gallary,
    ): Boolean {
        return oldItem == newItem
    }
}