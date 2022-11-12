package com.elkenany.views.local_stock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.LogoCardViewItemBinding

import com.elkenany.entities.stock_data.GeneralLogoData

class LocalStockLogosAdapter (private val partnerClickListener: ClickListener<GeneralLogoData>) :
    ListAdapter<GeneralLogoData, LocalStockLogoViewHolder>(
        LocalStockLogoDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalStockLogoViewHolder {
        return LocalStockLogoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocalStockLogoViewHolder, position: Int) {
        holder.bind(getItem(position)!!, partnerClickListener)
    }

}

class LocalStockLogoViewHolder private constructor(private val binding: LogoCardViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(logo: GeneralLogoData, partnerClickListener: ClickListener<GeneralLogoData>) {
        binding.data = logo
        binding.name = ""
        binding.image = logo.image
        binding.clickListener = partnerClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): LocalStockLogoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LogoCardViewItemBinding.inflate(layoutInflater, parent, false)
            return LocalStockLogoViewHolder(binding)
        }
    }
}


class LocalStockLogoDiffUtil : DiffUtil.ItemCallback<GeneralLogoData>() {
    override fun areItemsTheSame(oldItem: GeneralLogoData, newItem: GeneralLogoData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GeneralLogoData, newItem: GeneralLogoData): Boolean {
        return oldItem == newItem
    }
}
