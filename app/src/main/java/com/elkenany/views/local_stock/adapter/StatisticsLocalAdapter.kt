package com.elkenany.views.local_stock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.StatisticsRowItemBinding
import com.elkenany.entities.stock_data.LocalChangesMember

class StatisticsLocalAdapter(private val clickListener: ClickListener<LocalChangesMember>) :
    ListAdapter<LocalChangesMember, LocalChangesMemberViewHolder>(
        LocalChangesMemberAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LocalChangesMemberViewHolder {
        return LocalChangesMemberViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocalChangesMemberViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class LocalChangesMemberViewHolder private constructor(private val binding: StatisticsRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        subsection: LocalChangesMember,
        clickListener: ClickListener<LocalChangesMember>,
    ) {
        binding.localData = subsection
        binding.fodderData = null
        binding.cardView2.visibility = View.GONE
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): LocalChangesMemberViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = StatisticsRowItemBinding.inflate(layoutInflater, parent, false)
            return LocalChangesMemberViewHolder(binding)
        }
    }
}


class LocalChangesMemberAdapterDiffUtil : DiffUtil.ItemCallback<LocalChangesMember>() {
    override fun areItemsTheSame(
        oldItem: LocalChangesMember,
        newItem: LocalChangesMember,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: LocalChangesMember,
        newItem: LocalChangesMember,
    ): Boolean {
        return oldItem == newItem
    }
}