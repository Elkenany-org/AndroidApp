package com.example.elkenany.views.store.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.R
import com.example.elkenany.databinding.MessageCardItemBinding
import com.example.elkenany.entities.store.Massage

class MessagesAdapter(private val user: String) :
    ListAdapter<Massage, MassageDaumViewHolder>(
        MassageDaumAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MassageDaumViewHolder {
        return MassageDaumViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MassageDaumViewHolder, position: Int) {
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.anim))
        holder.bind(getItem(position)!!, user)
    }

}

class MassageDaumViewHolder private constructor(private val binding: MessageCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bind(message: Massage, user: String) {
        binding.data = message
        if (message.name == user) {
            binding.userOne.visibility = View.GONE
            binding.userTwo.visibility = View.VISIBLE
        } else {
            binding.userOne.visibility = View.VISIBLE
            binding.userTwo.visibility = View.GONE
        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): MassageDaumViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = MessageCardItemBinding.inflate(layoutInflater, parent, false)
            return MassageDaumViewHolder(binding)
        }
    }
}


class MassageDaumAdapterDiffUtil : DiffUtil.ItemCallback<Massage>() {
    override fun areItemsTheSame(oldItem: Massage, newItem: Massage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Massage, newItem: Massage): Boolean {
        return oldItem == newItem
    }
}