package com.example.elkenany.views.home.home_service.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.BigRecyclerItemBinding
import com.example.elkenany.entities.home_data.ServiceMagazine

class ServiceGuideAndMagazineAdapter(private val partnerClickListener: ClickListener<ServiceMagazine>) :
    ListAdapter<ServiceMagazine, ServiceGuideAndMagazineViewHolder>(
        ServiceGuideAndMagazineDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ServiceGuideAndMagazineViewHolder {
        return ServiceGuideAndMagazineViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ServiceGuideAndMagazineViewHolder, position: Int) {
        holder.bind(getItem(position)!!, partnerClickListener)
    }

}

class ServiceGuideAndMagazineViewHolder private constructor(private val binding: BigRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        magazine: ServiceMagazine,
        partnerClickListener: ClickListener<ServiceMagazine>,
    ) {
        binding.data = magazine
        binding.url = magazine.image
        binding.clickListener = partnerClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ServiceGuideAndMagazineViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return ServiceGuideAndMagazineViewHolder(binding)
        }
    }
}


class ServiceGuideAndMagazineDiffUtil : DiffUtil.ItemCallback<ServiceMagazine>() {
    override fun areItemsTheSame(
        oldItem: ServiceMagazine,
        newItem: ServiceMagazine,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ServiceMagazine,
        newItem: ServiceMagazine,
    ): Boolean {
        return oldItem == newItem
    }
}