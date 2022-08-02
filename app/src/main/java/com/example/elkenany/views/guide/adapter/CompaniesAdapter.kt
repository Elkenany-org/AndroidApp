package com.example.elkenany.views.guide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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
            binding.cardView.setBackgroundResource(R.color.green)
            binding.companyName.setTextColor(R.color.orange)
            binding.companyLocation.setTextColor(R.color.orange)
            binding.companyRate.setTextColor(R.color.orange)

        }

        binding.name = company.name
        binding.image = company.image
        binding.location = company.address
        binding.rate = " التقييم " + company.rate.toString()

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