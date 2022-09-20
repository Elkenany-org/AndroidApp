package com.elkenany.views.guide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.BigRecyclerItemWithDetailsBinding
import com.elkenany.entities.guide.GuideSubSection

class GuideSubSectionAdapter(private val sectorClickListener: ClickListener<GuideSubSection>) :
    ListAdapter<GuideSubSection, GuideSubSectionViewHolder>(
        GuideSubSectionAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GuideSubSectionViewHolder {
        return GuideSubSectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GuideSubSectionViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class GuideSubSectionViewHolder private constructor(private val binding: BigRecyclerItemWithDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        subsection: GuideSubSection,
        sectorClickListener: ClickListener<GuideSubSection>,
    ) {
        binding.data = subsection
        binding.title = subsection.name
        binding.url = subsection.image
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): GuideSubSectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemWithDetailsBinding.inflate(layoutInflater, parent, false)
            return GuideSubSectionViewHolder(binding)
        }
    }
}


class GuideSubSectionAdapterDiffUtil : DiffUtil.ItemCallback<GuideSubSection>() {
    override fun areItemsTheSame(
        oldItem: GuideSubSection,
        newItem: GuideSubSection,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GuideSubSection,
        newItem: GuideSubSection,
    ): Boolean {
        return oldItem == newItem
    }
}