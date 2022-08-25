package com.example.elkenany.views.local_stock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.BigRecyclerItemWithDetailsBinding
import com.example.elkenany.databinding.StatisticsRowItemBinding
import com.example.elkenany.entities.stock_data.ChangesMember

class StatisticsAdapter(private val clickListener: ClickListener<ChangesMember>) :
    ListAdapter<ChangesMember, ChangesMemberViewHolder>(
        ChangesMemberAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ChangesMemberViewHolder {
        return ChangesMemberViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChangesMemberViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ChangesMemberViewHolder private constructor(private val binding: StatisticsRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        subsection: ChangesMember,
        clickListener: ClickListener<ChangesMember>,
    ) {
        binding.data = subsection
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ChangesMemberViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = StatisticsRowItemBinding.inflate(layoutInflater, parent, false)
            return ChangesMemberViewHolder(binding)
        }
    }
}


class ChangesMemberAdapterDiffUtil : DiffUtil.ItemCallback<ChangesMember>() {
    override fun areItemsTheSame(
        oldItem: ChangesMember,
        newItem: ChangesMember,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ChangesMember,
        newItem: ChangesMember,
    ): Boolean {
        return oldItem == newItem
    }
}