package com.elkenany.views.about.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.SmallRecyclerItemBinding
import com.elkenany.entities.common.LogosAndBannersData

class SponsersAdapter(private val clickListener: ClickListener<LogosAndBannersData>) :
    ListAdapter<LogosAndBannersData, SponsersViewHolder>(
        SponsersDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsersViewHolder {
        return SponsersViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SponsersViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class SponsersViewHolder private constructor(private val binding: SmallRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(logo: LogosAndBannersData, clickListener: ClickListener<LogosAndBannersData>) {
        binding.data = logo
        binding.name = ""
        binding.image = logo.image
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): SponsersViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SmallRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return SponsersViewHolder(binding)
        }
    }
}


class SponsersDiffUtil : DiffUtil.ItemCallback<LogosAndBannersData>() {
    override fun areItemsTheSame(
        oldItem: LogosAndBannersData,
        newItem: LogosAndBannersData,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: LogosAndBannersData,
        newItem: LogosAndBannersData,
    ): Boolean {
        return oldItem == newItem
    }
}