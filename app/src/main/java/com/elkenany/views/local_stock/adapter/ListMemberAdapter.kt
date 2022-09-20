package com.elkenany.views.local_stock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.ArrayAdapterItemBinding
import com.elkenany.entities.stock_data.ListMember

class ListMemberAdapter(private val clickListener: ClickListener<ListMember>) :
    ListAdapter<ListMember, ListMemberViewHolder>(
        ListMemberAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListMemberViewHolder {
        return ListMemberViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListMemberViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ListMemberViewHolder private constructor(private val binding: ArrayAdapterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        subsection: ListMember,
        clickListener: ClickListener<ListMember>,
    ) {
        binding.member = subsection
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ListMemberViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ArrayAdapterItemBinding.inflate(layoutInflater, parent, false)
            return ListMemberViewHolder(binding)
        }
    }
}


class ListMemberAdapterDiffUtil : DiffUtil.ItemCallback<ListMember>() {
    override fun areItemsTheSame(
        oldItem: ListMember,
        newItem: ListMember,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ListMember,
        newItem: ListMember,
    ): Boolean {
        return oldItem == newItem
    }
}