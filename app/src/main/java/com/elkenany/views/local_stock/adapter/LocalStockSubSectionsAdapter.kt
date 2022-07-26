package com.elkenany.views.local_stock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.BigRecyclerItemWithDetailsBinding
import com.elkenany.entities.stock_data.LocalStockSubSection

class LocalStockSubSectionsAdapter(private val sectorClickListener: ClickListener<LocalStockSubSection>) :
    ListAdapter<LocalStockSubSection, LocalStockSubSectionsViewHolder>(
        LocalStockSubSectionAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LocalStockSubSectionsViewHolder {
        return LocalStockSubSectionsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocalStockSubSectionsViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class LocalStockSubSectionsViewHolder private constructor(private val binding: BigRecyclerItemWithDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        subsection: LocalStockSubSection,
        sectorClickListener: ClickListener<LocalStockSubSection>,
    ) {
        val logo: String? = try {
            subsection.logoIn.first()?.image
        } catch (e: Exception) {
            null
        }
        binding.data = subsection
        binding.title = subsection.name
        binding.url = subsection.image
        binding.logo = logo.toString()
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): LocalStockSubSectionsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemWithDetailsBinding.inflate(layoutInflater, parent, false)
            return LocalStockSubSectionsViewHolder(binding)
        }
    }
}


class LocalStockSubSectionAdapterDiffUtil : DiffUtil.ItemCallback<LocalStockSubSection>() {
    override fun areItemsTheSame(
        oldItem: LocalStockSubSection,
        newItem: LocalStockSubSection,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: LocalStockSubSection,
        newItem: LocalStockSubSection,
    ): Boolean {
        return oldItem == newItem
    }
}