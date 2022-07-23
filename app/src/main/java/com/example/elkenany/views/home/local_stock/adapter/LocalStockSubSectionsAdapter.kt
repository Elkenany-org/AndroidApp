package com.example.elkenany.views.home.local_stock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.SmallRecyclerItemBinding
import com.example.elkenany.entities.stock_data.LocalStockSubSection

class LocalStockSubSectionsAdapter(private val sectorClickListener: ClickListener<LocalStockSubSection>) :
    ListAdapter<LocalStockSubSection, LocalStockSubSectionsViewHolder>(
        LocalStockSubSectionAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocalStockSubSectionsViewHolder {
        return LocalStockSubSectionsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocalStockSubSectionsViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class LocalStockSubSectionsViewHolder private constructor(private val binding: SmallRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        subsection: LocalStockSubSection,
        sectorClickListener: ClickListener<LocalStockSubSection>
    ) {
        binding.data = subsection
        binding.name = subsection.name
        binding.image = subsection.image
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): LocalStockSubSectionsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SmallRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return LocalStockSubSectionsViewHolder(binding)
        }
    }
}


class LocalStockSubSectionAdapterDiffUtil : DiffUtil.ItemCallback<LocalStockSubSection>() {
    override fun areItemsTheSame(
        oldItem: LocalStockSubSection,
        newItem: LocalStockSubSection
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: LocalStockSubSection,
        newItem: LocalStockSubSection
    ): Boolean {
        return oldItem == newItem
    }
}