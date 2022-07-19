package com.example.elkenany.views.home.home_service.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.BigRecyclerItemWithDetailsBinding
import com.example.elkenany.entities.home_data.ServiceRecomandtion

class ServiceRecommendationAdapter(private val partnerClickListener: ClickListener<ServiceRecomandtion>) :
    ListAdapter<ServiceRecomandtion, ServiceRecommendationViewHolder>(
        ServiceRecomandtionDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ServiceRecommendationViewHolder {
        return ServiceRecommendationViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ServiceRecommendationViewHolder, position: Int) {
        holder.bind(getItem(position)!!, partnerClickListener)
    }

}

class ServiceRecommendationViewHolder private constructor(private val binding: BigRecyclerItemWithDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        recommendation: ServiceRecomandtion,
        partnerClickListener: ClickListener<ServiceRecomandtion>,
    ) {
        binding.data = recommendation
        binding.title = recommendation.name
        binding.url = recommendation.image
        binding.clickListener = partnerClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ServiceRecommendationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemWithDetailsBinding.inflate(layoutInflater, parent, false)
            return ServiceRecommendationViewHolder(binding)
        }
    }
}


class ServiceRecomandtionDiffUtil : DiffUtil.ItemCallback<ServiceRecomandtion>() {
    override fun areItemsTheSame(
        oldItem: ServiceRecomandtion,
        newItem: ServiceRecomandtion,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ServiceRecomandtion,
        newItem: ServiceRecomandtion,
    ): Boolean {
        return oldItem == newItem
    }
}