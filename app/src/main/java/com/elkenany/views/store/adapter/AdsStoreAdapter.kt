package com.elkenany.views.store.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.StoreCardViewItemBinding
import com.elkenany.entities.store.AdsStoreDaum
import java.text.SimpleDateFormat

class AdsStoreAdapter(private val sectorClickListener: ClickListener<AdsStoreDaum>) :
    ListAdapter<AdsStoreDaum, AdsStoreDaumViewHolder>(
        AdsStoreDaumAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsStoreDaumViewHolder {
        return AdsStoreDaumViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AdsStoreDaumViewHolder, position: Int) {
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.anim))
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class AdsStoreDaumViewHolder private constructor(private val binding: StoreCardViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bind(ads: AdsStoreDaum, sectorClickListener: ClickListener<AdsStoreDaum>) {
        binding.data = ads
        binding.title = ads.title
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val text = ads.createdAt
        val date = text?.let { formatter.parse(it) }
        binding.createdAt = date?.let { formatter.format(it).toString() }
        binding.location = ads.address
        binding.url = ads.image
        binding.price = ads.salary.toString()
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): AdsStoreDaumViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = StoreCardViewItemBinding.inflate(layoutInflater, parent, false)
            return AdsStoreDaumViewHolder(binding)
        }
    }
}


class AdsStoreDaumAdapterDiffUtil : DiffUtil.ItemCallback<AdsStoreDaum>() {
    override fun areItemsTheSame(oldItem: AdsStoreDaum, newItem: AdsStoreDaum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AdsStoreDaum, newItem: AdsStoreDaum): Boolean {
        return oldItem == newItem
    }
}