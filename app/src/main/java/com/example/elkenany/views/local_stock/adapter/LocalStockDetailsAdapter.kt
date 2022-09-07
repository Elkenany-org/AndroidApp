package com.example.elkenany.views.local_stock.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.TableRowItemBinding
import com.example.elkenany.entities.stock_data.ColumnsData

class LocalStockDetailsAdapter(private val clickListener: ClickListener<ColumnsData>) :
    ListAdapter<ColumnsData, ColumnsDataViewHolder>(
        ColumnsDataAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ColumnsDataViewHolder {
        return ColumnsDataViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ColumnsDataViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ColumnsDataViewHolder private constructor(private val binding: TableRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        columnsData: ColumnsData,
        clickListener: ClickListener<ColumnsData>,
    ) {
        binding.data = columnsData
        if (columnsData.type == "1") {
            binding.cardView.setCardBackgroundColor(itemView.context.getColor(R.color.orange))
        } else {
            binding.cardView.setCardBackgroundColor(itemView.context.getColor(R.color.white))
        }
        try {
            if (!columnsData.statistics!!.startsWith("h")) {
                binding.apply {
                    title = columnsData.statistics
                    image = null
                }
            } else {
                binding.apply {
                    title = null
                    image = columnsData.statistics
                }
            }
        }catch (e:Exception){
            Log.i("LocalStockDetailsAdapterException",e.message.toString())
        }

        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ColumnsDataViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TableRowItemBinding.inflate(layoutInflater, parent, false)
            return ColumnsDataViewHolder(binding)
        }
    }
}


class ColumnsDataAdapterDiffUtil : DiffUtil.ItemCallback<ColumnsData>() {
    override fun areItemsTheSame(
        oldItem: ColumnsData,
        newItem: ColumnsData,
    ): Boolean {
        return oldItem.memId == oldItem.memId
    }

    override fun areContentsTheSame(
        oldItem: ColumnsData,
        newItem: ColumnsData,
    ): Boolean {
        return oldItem == newItem
    }
}