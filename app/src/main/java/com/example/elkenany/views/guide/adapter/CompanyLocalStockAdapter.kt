package com.example.elkenany.views.guide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.SmallRecyclerItemBinding
import com.example.elkenany.entities.guide.Localstock

class CompanyLocalStockAdapter(private val sectorClickListener: ClickListener<Localstock>) :
    ListAdapter<Localstock, CompaniesLocalstockViewHolder>(
        LocalstockAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CompaniesLocalstockViewHolder {
        return CompaniesLocalstockViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CompaniesLocalstockViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class CompaniesLocalstockViewHolder private constructor(private val binding: SmallRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        company: Localstock,
        sectorClickListener: ClickListener<Localstock>,
    ) {
        binding.data = company
        binding.name = company.name
        binding.image = company.image
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CompaniesLocalstockViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SmallRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return CompaniesLocalstockViewHolder(binding)
        }
    }
}


class LocalstockAdapterDiffUtil : DiffUtil.ItemCallback<Localstock>() {
    override fun areItemsTheSame(
        oldItem: Localstock,
        newItem: Localstock,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Localstock,
        newItem: Localstock,
    ): Boolean {
        return oldItem == newItem
    }
}