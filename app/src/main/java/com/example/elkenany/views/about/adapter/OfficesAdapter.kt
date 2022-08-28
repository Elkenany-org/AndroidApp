package com.example.elkenany.views.about.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.AboutBranchItemCardBinding
import com.example.elkenany.entities.home_data.Office

class OfficesAdapter(private val clickListener: ClickListener<Office>) :
    ListAdapter<Office, OfficeViewHolder>(
        OfficeAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OfficeViewHolder {
        return OfficeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OfficeViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class OfficeViewHolder private constructor(private val binding: AboutBranchItemCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        office: Office,
        clickListener: ClickListener<Office>,
    ) {
        binding.data = office
//        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): OfficeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AboutBranchItemCardBinding.inflate(layoutInflater, parent, false)
            return OfficeViewHolder(binding)
        }
    }
}


class OfficeAdapterDiffUtil : DiffUtil.ItemCallback<Office>() {
    override fun areItemsTheSame(
        oldItem: Office,
        newItem: Office,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Office,
        newItem: Office,
    ): Boolean {
        return oldItem == newItem
    }
}