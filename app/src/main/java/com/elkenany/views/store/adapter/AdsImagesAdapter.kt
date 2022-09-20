package com.elkenany.views.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.ImageCardItemBinding
import com.elkenany.entities.store.AdsImage

class AdsImagesAdapter(private val sectorClickListener: ClickListener<AdsImage>) :
    ListAdapter<AdsImage, AdsImagesViewHolder>(
        AdsImagesAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsImagesViewHolder {
        return AdsImagesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AdsImagesViewHolder, position: Int) {
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.anim))
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class AdsImagesViewHolder private constructor(private val binding: ImageCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(ads: AdsImage, sectorClickListener: ClickListener<AdsImage>) {
        binding.data = ads
        binding.image = ads.image
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): AdsImagesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ImageCardItemBinding.inflate(layoutInflater, parent, false)
            return AdsImagesViewHolder(binding)
        }
    }
}


class AdsImagesAdapterDiffUtil : DiffUtil.ItemCallback<AdsImage>() {
    override fun areItemsTheSame(oldItem: AdsImage, newItem: AdsImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AdsImage, newItem: AdsImage): Boolean {
        return oldItem == newItem
    }
}