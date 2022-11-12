package com.elkenany.views.tenders.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.BigRecyclerItemWithDetailsBinding
import com.elkenany.entities.tenders.TendersSection

class TendersSubSectionsAdapter(private val clickListener: ClickListener<TendersSection>) :
    ListAdapter<TendersSection, TendersSubSectionViewHolder>(
        TendersSectionDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TendersSubSectionViewHolder {
        return TendersSubSectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TendersSubSectionViewHolder, position: Int) {
        holder.itemView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.anim
            )
        )
        holder.bind(getItem(position)!!, clickListener)
    }

}

class TendersSubSectionViewHolder private constructor(private val binding: BigRecyclerItemWithDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: TendersSection, clickListener: ClickListener<TendersSection>) {
        binding.data = data
        binding.url = data.image
        binding.title = data.name
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TendersSubSectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemWithDetailsBinding.inflate(layoutInflater, parent, false)
            return TendersSubSectionViewHolder(binding)
        }
    }
}


class TendersSectionDiffUtil : DiffUtil.ItemCallback<TendersSection>() {
    override fun areItemsTheSame(oldItem: TendersSection, newItem: TendersSection): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TendersSection, newItem: TendersSection): Boolean {
        return oldItem == newItem
    }
}