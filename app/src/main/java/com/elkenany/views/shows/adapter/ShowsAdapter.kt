package com.elkenany.views.shows.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.ShowsCardItemBinding
import com.elkenany.entities.shows_data.ShowsData
import java.text.SimpleDateFormat

class ShowsAdapter(private val clickListener: ClickListener<ShowsData>) :
    ListAdapter<ShowsData, ShowsAdapterViewHolder>(
        ShowsAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsAdapterViewHolder {
        return ShowsAdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShowsAdapterViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ShowsAdapterViewHolder private constructor(private val binding: ShowsCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bind(data: ShowsData, clickListener: ClickListener<ShowsData>) {
        binding.data = data
        binding.followers = "${data.viewCount} متابع"
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val text = data.date
        val date = text?.let { formatter.parse(it) }
        binding.date = date?.let { formatter.format(it).toString() }
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ShowsAdapterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ShowsCardItemBinding.inflate(layoutInflater, parent, false)
            return ShowsAdapterViewHolder(binding)
        }
    }
}


class ShowsAdapterDiffUtil : DiffUtil.ItemCallback<ShowsData>() {
    override fun areItemsTheSame(oldItem: ShowsData, newItem: ShowsData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShowsData, newItem: ShowsData): Boolean {
        return oldItem == newItem
    }
}