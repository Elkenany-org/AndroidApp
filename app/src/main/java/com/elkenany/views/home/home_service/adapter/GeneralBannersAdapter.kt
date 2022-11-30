package com.elkenany.views.home.home_service.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.BannersCardViewItemBinding
import com.elkenany.entities.common.LogosAndBannersData

class GeneralBannersAdapter(private val clickListener: ClickListener<LogosAndBannersData>) :
    ListAdapter<LogosAndBannersData, LocalStockBannerViewHolder>(
        LocalStockBannerAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalStockBannerViewHolder {
        return LocalStockBannerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocalStockBannerViewHolder, position: Int) {
//        holder.itemView
//            .startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,
//                com.facebook.login.R.anim.abc_fade_in))
        holder.bind(getItem(position)!!, clickListener)
    }

}

class LocalStockBannerViewHolder private constructor(private val binding: BannersCardViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(banner: LogosAndBannersData, clickListener: ClickListener<LogosAndBannersData>) {
        binding.data = banner
        binding.url = banner.image
        binding.clickListener = clickListener
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


class LocalStockBannerAdapterDiffUtil : DiffUtil.ItemCallback<LogosAndBannersData>() {
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