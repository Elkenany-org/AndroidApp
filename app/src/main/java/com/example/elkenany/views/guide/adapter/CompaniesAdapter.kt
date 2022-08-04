package com.example.elkenany.views.guide.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.colorResource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.CompanyItemBinding
import com.example.elkenany.entities.guide.CompaniesDaum

class CompaniesAdapter(private val sectorClickListener: ClickListener<CompaniesDaum>) :
    ListAdapter<CompaniesDaum, CompaniesDaumViewHolder>(
        CompaniesDaumAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CompaniesDaumViewHolder {
        return CompaniesDaumViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CompaniesDaumViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class CompaniesDaumViewHolder private constructor(private val binding: CompanyItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        company: CompaniesDaum,
        sectorClickListener: ClickListener<CompaniesDaum>,
    ) {
        binding.data = company
        if (company.sponser == 1) {
            binding.cardView.setCardBackgroundColor(binding.root.context.getColor(R.color.green))
            binding.companyName.setTextColor(binding.root.context.getColor(R.color.orange))
            binding.companyLocation.setTextColor(binding.root.context.getColor(R.color.orange))
            binding.companyRate.apply {
                visibility = View.GONE
            }
        }
        binding.name = company.name
        binding.image = company.image
        binding.location = company.address
        binding.companyRate.apply {
            rating = company.rate!!.toFloat()
        }

        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CompaniesDaumViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CompanyItemBinding.inflate(layoutInflater, parent, false)
            return CompaniesDaumViewHolder(binding)
        }
    }
}


class CompaniesDaumAdapterDiffUtil : DiffUtil.ItemCallback<CompaniesDaum>() {
    override fun areItemsTheSame(
        oldItem: CompaniesDaum,
        newItem: CompaniesDaum,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CompaniesDaum,
        newItem: CompaniesDaum,
    ): Boolean {
        return oldItem == newItem
    }
}