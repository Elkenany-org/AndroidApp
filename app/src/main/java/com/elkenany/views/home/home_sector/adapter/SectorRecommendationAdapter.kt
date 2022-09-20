package com.elkenany.views.home.home_sector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.BigRecyclerItemBinding
import com.elkenany.entities.home_data.SectorsRecomandtion

class SectorRecommendationAdapter(private val recommendationClickListener: ClickListener<SectorsRecomandtion>) :
    ListAdapter<SectorsRecomandtion, SectorRecommendationViewHolder>(
        SectorRecommendationAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SectorRecommendationViewHolder {
        return SectorRecommendationViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SectorRecommendationViewHolder, position: Int) {
        holder.bind(getItem(position)!!, recommendationClickListener)
    }


}

class SectorRecommendationViewHolder private constructor(private val binding: BigRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        recomandtion: SectorsRecomandtion,
        recommendationClickListener: ClickListener<SectorsRecomandtion>,
    ) {
        binding.data = recomandtion
        binding.url = recomandtion.image
        binding.clickListener = recommendationClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): SectorRecommendationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return SectorRecommendationViewHolder(binding)
        }
    }
}


class SectorRecommendationAdapterDiffUtil : DiffUtil.ItemCallback<SectorsRecomandtion>() {
    override fun areItemsTheSame(
        oldItem: SectorsRecomandtion,
        newItem: SectorsRecomandtion,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SectorsRecomandtion,
        newItem: SectorsRecomandtion,
    ): Boolean {
        return oldItem == newItem
    }
}

