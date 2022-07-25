package com.example.elkenany.views.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.SearchResultItemBinding
import com.example.elkenany.entities.search.Result


class SearchResultsAdapter(private val sectorClickListener: ClickListener<Result>) :
    ListAdapter<Result, ResultViewHolder>(
        ResultAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position)!!, sectorClickListener)
    }

}

class ResultViewHolder private constructor(private val binding: SearchResultItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(result: Result, sectorClickListener: ClickListener<Result>) {
        binding.data = result
        binding.title = result.name
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ResultViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SearchResultItemBinding.inflate(layoutInflater, parent, false)
            return ResultViewHolder(binding)
        }
    }
}


class ResultAdapterDiffUtil : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}