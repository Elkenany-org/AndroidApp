package com.elkenany.views.home.home_service.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.LogoCardViewItemBinding
import com.elkenany.entities.common.LogosAndBannersData

class GeneralLogosAdapter(private val clickListener: ClickListener<LogosAndBannersData>) :
    ListAdapter<LogosAndBannersData, ServicePartnerViewHolder>(
        GeneralLogosDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicePartnerViewHolder {
        return ServicePartnerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ServicePartnerViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ServicePartnerViewHolder private constructor(private val binding: LogoCardViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(logo: LogosAndBannersData, clickListener: ClickListener<LogosAndBannersData>) {
        binding.data = logo
        binding.name = ""
        binding.image = logo.image
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ServicePartnerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LogoCardViewItemBinding.inflate(layoutInflater, parent, false)
            return ServicePartnerViewHolder(binding)
        }
    }
}


class GeneralLogosDiffUtil : DiffUtil.ItemCallback<LogosAndBannersData>() {
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