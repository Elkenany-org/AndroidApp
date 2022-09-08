package com.example.elkenany.views.store.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.MyAdsCardItemBinding
import com.example.elkenany.entities.store.MyAdsDaum
import java.text.SimpleDateFormat

class MyAdsAdapter(
    private val clickListener: ClickListener<MyAdsDaum>,
    private val removeAdClickListener: ClickListener<MyAdsDaum>,
) :
    ListAdapter<MyAdsDaum, MyAdsDaumViewHolder>(
        MyAdsDaumAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdsDaumViewHolder {
        return MyAdsDaumViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyAdsDaumViewHolder, position: Int) {
        holder.itemView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.anim
            )
        )
        holder.bind(getItem(position)!!, clickListener, removeAdClickListener)
    }

}

class MyAdsDaumViewHolder private constructor(private val binding: MyAdsCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bind(
        myAds: MyAdsDaum,
        clickListener: ClickListener<MyAdsDaum>,
        removeAdClickListener: ClickListener<MyAdsDaum>,
    ) {
        binding.data = myAds
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val text = myAds.createdAt
        val date = text?.let { formatter.parse(it) }
        binding.createdAt = date?.let { formatter.format(it).toString() }
        binding.price = if (myAds.salary == null) {
            null
        } else {
            myAds.salary.toString() + " جنية "
        }
        binding.clickListener = clickListener
        binding.deleteAd = removeAdClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): MyAdsDaumViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = MyAdsCardItemBinding.inflate(layoutInflater, parent, false)
            return MyAdsDaumViewHolder(binding)
        }
    }
}


class MyAdsDaumAdapterDiffUtil : DiffUtil.ItemCallback<MyAdsDaum>() {
    override fun areItemsTheSame(oldItem: MyAdsDaum, newItem: MyAdsDaum): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MyAdsDaum, newItem: MyAdsDaum): Boolean {
        return oldItem == newItem
    }
}