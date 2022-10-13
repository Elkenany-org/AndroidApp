package com.elkenany.views.home.home_service.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.SmallRecyclerItemBinding
import com.elkenany.entities.home_data.HomeServiceDaum

class HomeServiceAdapter(private val partnerClickListener: ClickListener<HomeServiceDaum>) :
    ListAdapter<HomeServiceDaum, HomeServiceViewHolder>(
        HomeServiceAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeServiceViewHolder {
        return HomeServiceViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeServiceViewHolder, position: Int) {
        holder.bind(getItem(position)!!, partnerClickListener)
    }

}

class HomeServiceViewHolder private constructor(private val binding: SmallRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        magazine: HomeServiceDaum,
        partnerClickListener: ClickListener<HomeServiceDaum>,
    ) {
        binding.data = magazine
        binding.image = magazine.image
        binding.clickListener = partnerClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): HomeServiceViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SmallRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return HomeServiceViewHolder(binding)
        }
    }
}


class HomeServiceAdapterDiffUtil : DiffUtil.ItemCallback<HomeServiceDaum>() {
    override fun areItemsTheSame(
        oldItem: HomeServiceDaum,
        newItem: HomeServiceDaum,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: HomeServiceDaum,
        newItem: HomeServiceDaum,
    ): Boolean {
        return oldItem == newItem
    }
}