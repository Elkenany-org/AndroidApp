package com.elkenany.views.shows.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.ImageCardItemBinding
import com.elkenany.entities.shows_data.ShowsImage

class ShowsImageAdapter(private val clickListener: ClickListener<ShowsImage>) :
    ListAdapter<ShowsImage, ShowsImageViewHolder>(
        ShowsImageAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsImageViewHolder {
        return ShowsImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShowsImageViewHolder, position: Int) {
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.anim))
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ShowsImageViewHolder private constructor(private val binding: ImageCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(ads: ShowsImage, clickListener: ClickListener<ShowsImage>) {
        binding.data = ads
        binding.bigImage = ads.image
        binding.smallmage = null
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ShowsImageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ImageCardItemBinding.inflate(layoutInflater, parent, false)
            return ShowsImageViewHolder(binding)
        }
    }
}


class ShowsImageAdapterDiffUtil : DiffUtil.ItemCallback<ShowsImage>() {
    override fun areItemsTheSame(oldItem: ShowsImage, newItem: ShowsImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShowsImage, newItem: ShowsImage): Boolean {
        return oldItem == newItem
    }
}