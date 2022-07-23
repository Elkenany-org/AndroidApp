package com.example.elkenany.views.home.local_stock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.SmallRecyclerItemBinding

import com.example.elkenany.entities.stock_data.LocalStockLogo

class LocalStockLogosAdapter (private val partnerClickListener: ClickListener<LocalStockLogo>) :
    ListAdapter<LocalStockLogo, LocalStockLogoViewHolder>(
        LocalStockLogoDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalStockLogoViewHolder {
        return LocalStockLogoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocalStockLogoViewHolder, position: Int) {
        holder.bind(getItem(position)!!, partnerClickListener)
    }

}

class LocalStockLogoViewHolder private constructor(private val binding: SmallRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(logo: LocalStockLogo, partnerClickListener: ClickListener<LocalStockLogo>) {
        binding.data = logo
        binding.name = ""
        binding.image = logo.image
        binding.clickListener = partnerClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): LocalStockLogoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SmallRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return LocalStockLogoViewHolder(binding)
        }
    }
}


class LocalStockLogoDiffUtil : DiffUtil.ItemCallback<LocalStockLogo>() {
    override fun areItemsTheSame(oldItem: LocalStockLogo, newItem: LocalStockLogo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocalStockLogo, newItem: LocalStockLogo): Boolean {
        return oldItem == newItem
    }
}
