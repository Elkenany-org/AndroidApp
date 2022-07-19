package com.example.elkenany.views.home.home_service.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.BigRecyclerItemBinding
import com.example.elkenany.entities.home_data.ServiceShow

class ServiceShowsAdapter(private val partnerClickListener: ClickListener<ServiceShow>) :
    ListAdapter<ServiceShow, ServiceShowsViewHolder>(
        ServiceShowsDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ServiceShowsViewHolder {
        return ServiceShowsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ServiceShowsViewHolder, position: Int) {
        holder.bind(getItem(position)!!, partnerClickListener)
    }

}

class ServiceShowsViewHolder private constructor(private val binding: BigRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        show: ServiceShow,
        partnerClickListener: ClickListener<ServiceShow>,
    ) {
        binding.data = show
        binding.url = show.image
        binding.clickListener = partnerClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ServiceShowsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return ServiceShowsViewHolder(binding)
        }
    }
}


class ServiceShowsDiffUtil : DiffUtil.ItemCallback<ServiceShow>() {
    override fun areItemsTheSame(
        oldItem: ServiceShow,
        newItem: ServiceShow,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ServiceShow,
        newItem: ServiceShow,
    ): Boolean {
        return oldItem == newItem
    }
}