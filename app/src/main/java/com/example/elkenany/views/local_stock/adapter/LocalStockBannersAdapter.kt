package com.example.elkenany.views.local_stock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.BannersCardViewItemBinding
import com.example.elkenany.databinding.BigRecyclerItemBinding
import com.example.elkenany.entities.stock_data.LocalStockBanner

class LocalStockBannersAdapter(private val sectorClickListener: ClickListener<LocalStockBanner>) :
    ListAdapter<LocalStockBanner, LocalStockBannerViewHolder>(
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

    fun bind(banner: LocalStockBanner, sectorClickListener: ClickListener<LocalStockBanner>) {
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


class LocalStockBannerAdapterDiffUtil : DiffUtil.ItemCallback<LocalStockBanner>() {
    override fun areItemsTheSame(oldItem: LocalStockBanner, newItem: LocalStockBanner): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocalStockBanner, newItem: LocalStockBanner): Boolean {
        return oldItem == newItem
    }
}