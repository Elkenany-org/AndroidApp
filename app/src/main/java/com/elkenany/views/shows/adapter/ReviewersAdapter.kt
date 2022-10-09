package com.elkenany.views.shows.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.ShowReviewersCardItemBinding
import com.elkenany.entities.shows_data.Review


class ReviewersAdapter(private val clickListener: ClickListener<Review>) :
    ListAdapter<Review, ReviewAdapterViewHolder>(
        ReviewAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapterViewHolder {
        return ReviewAdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReviewAdapterViewHolder, position: Int) {
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.anim))
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ReviewAdapterViewHolder private constructor(private val binding: ShowReviewersCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(ads: Review, clickListener: ClickListener<Review>) {
        binding.data = ads
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ReviewAdapterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ShowReviewersCardItemBinding.inflate(layoutInflater, parent, false)
            return ReviewAdapterViewHolder(binding)
        }
    }
}


class ReviewAdapterDiffUtil : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}