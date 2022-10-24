package com.elkenany.views.recruitment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.CategoryItemBinding
import com.elkenany.entities.recruitment.Category


class JobCategoriesAdapter(
    private val clickListener: ClickListener<Category>,
) :
    ListAdapter<Category, JobCategoriesViewHolder>(
        JobCategoriesDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobCategoriesViewHolder {
        return JobCategoriesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: JobCategoriesViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class JobCategoriesViewHolder private constructor(private val binding: CategoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: Category,
        clickListener: ClickListener<Category>,

        ) {
        binding.data = data
        binding.name = data.name
        when (data.selected.toString()) {
            "1" -> {
                binding.indicator.apply {
                    visibility = View.VISIBLE
                    setCardBackgroundColor(binding.root.context.getColor(R.color.orange))
                }
            }
            else -> {
                binding.indicator.visibility = View.GONE
            }
        }
        binding.clickListener = clickListener
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): JobCategoriesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CategoryItemBinding.inflate(layoutInflater, parent, false)
            return JobCategoriesViewHolder(binding)
        }
    }
}


class JobCategoriesDiffUtil : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}