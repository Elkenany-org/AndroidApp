package com.elkenany.views.local_stock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.StatisticsRowItemBinding
import com.elkenany.entities.stock_data.FodderChangesMember

class StatisticsFodderAdapter(private val clickListener: ClickListener<FodderChangesMember>) :
    ListAdapter<FodderChangesMember, FodderChangesMemberViewHolder>(
        FodderChangesMemberAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FodderChangesMemberViewHolder {
        return FodderChangesMemberViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FodderChangesMemberViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class FodderChangesMemberViewHolder private constructor(private val binding: StatisticsRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        subsection: FodderChangesMember,
        clickListener: ClickListener<FodderChangesMember>,
    ) {
        binding.localData = null
        binding.fodderData = subsection
        binding.cardView1.visibility = View.GONE
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): FodderChangesMemberViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = StatisticsRowItemBinding.inflate(layoutInflater, parent, false)
            return FodderChangesMemberViewHolder(binding)
        }
    }
}


class FodderChangesMemberAdapterDiffUtil : DiffUtil.ItemCallback<FodderChangesMember>() {
    override fun areItemsTheSame(
        oldItem: FodderChangesMember,
        newItem: FodderChangesMember,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: FodderChangesMember,
        newItem: FodderChangesMember,
    ): Boolean {
        return oldItem == newItem
    }
}