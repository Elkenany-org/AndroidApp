package com.example.elkenany.views.home.home_service.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.SmallRecyclerItemBinding
import com.example.elkenany.entities.home_data.SectorsLogo
import com.example.elkenany.entities.home_data.ServiceLogo

class ServicePartnerAdapter (private val partnerClickListener: ClickListener<ServiceLogo>) :
    ListAdapter<ServiceLogo, ServicePartnerViewHolder>(
        ServicePartnerDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicePartnerViewHolder {
        return ServicePartnerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ServicePartnerViewHolder, position: Int) {
        holder.bind(getItem(position)!!, partnerClickListener)
    }

}

class ServicePartnerViewHolder private constructor(private val binding: SmallRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(logo: ServiceLogo, partnerClickListener: ClickListener<ServiceLogo>) {
        binding.data = logo
        binding.name = ""
        binding.image = logo.image
        binding.clickListener = partnerClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ServicePartnerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SmallRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return ServicePartnerViewHolder(binding)
        }
    }
}


class ServicePartnerDiffUtil : DiffUtil.ItemCallback<ServiceLogo>() {
    override fun areItemsTheSame(oldItem: ServiceLogo, newItem: ServiceLogo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ServiceLogo, newItem: ServiceLogo): Boolean {
        return oldItem == newItem
    }
}