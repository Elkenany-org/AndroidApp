package com.elkenany.views.local_stock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.BannersCardViewItemBinding
import com.elkenany.entities.stock_data.GeneralBannerData

class GeneralBannersAdapter(private val sectorClickListener: ClickListener<GeneralBannerData>) :
    ListAdapter<GeneralBannerData, LocalStockBannerViewHolder>(
        LocalStockBannerAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalStockBannerViewHolder {
        return LocalStockBannerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocalStockBannerViewHolder, position: Int) {
//        holder.itemView
//            .startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,
//                com.facebook.login.R.anim.abc_fade_in))
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class LocalStockBannerViewHolder private constructor(private val binding: BannersCardViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(banner: GeneralBannerData, sectorClickListener: ClickListener<GeneralBannerData>) {
        binding.data = banner
        binding.url = banner.image
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): LocalStockBannerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BannersCardViewItemBinding.inflate(layoutInflater, parent, false)
            return LocalStockBannerViewHolder(binding)
        }
    }
}


class LocalStockBannerAdapterDiffUtil : DiffUtil.ItemCallback<GeneralBannerData>() {
    override fun areItemsTheSame(oldItem: GeneralBannerData, newItem: GeneralBannerData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GeneralBannerData, newItem: GeneralBannerData): Boolean {
        return oldItem == newItem
    }
}