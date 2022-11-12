package com.elkenany.views.tenders.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.BigRecyclerItemWithDetailsBinding
import com.elkenany.entities.tenders.TendersDaum


class TendersListAdapter(private val clickListener: ClickListener<TendersDaum>) :
    ListAdapter<TendersDaum, TendersListViewHolder>(
        TendersListDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TendersListViewHolder {
        return TendersListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TendersListViewHolder, position: Int) {
        holder.itemView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.anim
            )
        )
        holder.bind(getItem(position)!!, clickListener)
    }

}

class TendersListViewHolder private constructor(private val binding: BigRecyclerItemWithDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: TendersDaum, clickListener: ClickListener<TendersDaum>) {
        binding.data = data
        binding.url = data.image
        binding.title = data.title
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TendersListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BigRecyclerItemWithDetailsBinding.inflate(layoutInflater, parent, false)
            return TendersListViewHolder(binding)
        }
    }
}


class TendersListDiffUtil : DiffUtil.ItemCallback<TendersDaum>() {
    override fun areItemsTheSame(oldItem: TendersDaum, newItem: TendersDaum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TendersDaum, newItem: TendersDaum): Boolean {
        return oldItem == newItem
    }
}