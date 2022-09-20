package com.elkenany.views.store.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.ChatCardItemBinding
import com.elkenany.entities.store.Chat
import java.text.SimpleDateFormat

class ChatsListAdapter(private val clickListener: ClickListener<Chat>) :
    ListAdapter<Chat, ChatDaumViewHolder>(
        ChatDaumAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatDaumViewHolder {
        return ChatDaumViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChatDaumViewHolder, position: Int) {
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.anim))
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ChatDaumViewHolder private constructor(private val binding: ChatCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bind(chat: Chat, clickListener: ClickListener<Chat>) {
        binding.data = chat
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val text = chat.createdAt
        val date = text?.let { formatter.parse(it) }
        binding.date = date?.let { formatter.format(it).toString() }
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ChatDaumViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ChatCardItemBinding.inflate(layoutInflater, parent, false)
            return ChatDaumViewHolder(binding)
        }
    }
}


class ChatDaumAdapterDiffUtil : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }
}