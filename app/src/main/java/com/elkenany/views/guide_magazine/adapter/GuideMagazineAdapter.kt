package com.elkenany.views.guide_magazine.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.MagazineCardItemBinding
import com.elkenany.entities.guide_magazine.MagazineDaum
import java.text.SimpleDateFormat

class GuideMagazineAdapter(private val clickListener: ClickListener<MagazineDaum>) :
    ListAdapter<MagazineDaum, ShowsAdapterViewHolder>(
        ShowsAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsAdapterViewHolder {
        return ShowsAdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShowsAdapterViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ShowsAdapterViewHolder private constructor(private val binding: MagazineCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bind(data: MagazineDaum, clickListener: ClickListener<MagazineDaum>) {
        binding.data = data
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ShowsAdapterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = MagazineCardItemBinding.inflate(layoutInflater, parent, false)
            return ShowsAdapterViewHolder(binding)
        }
    }
}


class ShowsAdapterDiffUtil : DiffUtil.ItemCallback<MagazineDaum>() {
    override fun areItemsTheSame(oldItem: MagazineDaum, newItem: MagazineDaum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MagazineDaum, newItem: MagazineDaum): Boolean {
        return oldItem == newItem
    }
}