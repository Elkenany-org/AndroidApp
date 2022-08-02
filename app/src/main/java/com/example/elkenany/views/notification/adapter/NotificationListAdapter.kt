package com.example.elkenany.views.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.databinding.NotificationCardViewItemBinding
import com.example.elkenany.entities.home_data.Nots

class NotificationListAdapter(private val notificationListener: ClickListener<Nots>) :
    ListAdapter<Nots, NotsAdapterViewHolder>(
        NotsAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotsAdapterViewHolder {
        return NotsAdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NotsAdapterViewHolder, position: Int) {
        holder.bind(getItem(position)!!, notificationListener)
    }

}

class NotsAdapterViewHolder private constructor(private val binding: NotificationCardViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(nots: Nots, sectorClickListener: ClickListener<Nots>) {
        binding.data = nots
        binding.image = nots.image
        binding.mainTitle = nots.title
        binding.describe = nots.desc
        binding.clickListener = sectorClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): NotsAdapterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NotificationCardViewItemBinding.inflate(layoutInflater, parent, false)
            return NotsAdapterViewHolder(binding)
        }
    }
}


class NotsAdapterDiffUtil : DiffUtil.ItemCallback<Nots>() {
    override fun areItemsTheSame(oldItem: Nots, newItem: Nots): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Nots, newItem: Nots): Boolean {
        return oldItem == newItem
    }
}