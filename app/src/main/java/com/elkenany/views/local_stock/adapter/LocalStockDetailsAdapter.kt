package com.elkenany.views.local_stock.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.R
import com.elkenany.databinding.TableRowItemBinding
import com.elkenany.entities.stock_data.ColumnsData

class LocalStockDetailsAdapter(
    private val clickListener: ClickListener<ColumnsData>,
    private val companyListener: ClickListener<ColumnsData>,
) :
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
        holder.bind(getItem(position)!!, clickListener, companyListener)
    }

}

class ColumnsDataViewHolder private constructor(private val binding: TableRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        columnsData: ColumnsData,
        clickListener: ClickListener<ColumnsData>,
        companyListener: ClickListener<ColumnsData>,
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
        } catch (e: Exception) {
            Log.i("LocalStockDetailsAdapterException", e.message.toString())
        }
        if (columnsData.name.isNullOrEmpty()) {
            binding.compNameView.visibility = View.GONE
        } else {
            binding.compNameView.visibility = View.VISIBLE
        }
        if (columnsData.price.isNullOrEmpty()) {
            binding.prodPriceView.visibility = View.GONE
        } else {
            binding.prodPriceView.visibility = View.VISIBLE
        }
        if (columnsData.change.isNullOrEmpty()) {
            binding.prodChangeView.visibility = View.GONE
        } else {
            binding.prodChangeView.visibility = View.VISIBLE
        }
        if (columnsData.chargingSystem.isNullOrEmpty()) {
            binding.charginSystemView.visibility = View.GONE
        } else {
            binding.charginSystemView.visibility = View.VISIBLE
        }
        if (columnsData.categorizeName.isNullOrEmpty()) {
            binding.categorizeNameView.visibility = View.GONE
        } else {
            binding.categorizeNameView.visibility = View.VISIBLE
        }
        if (columnsData.weight.isNullOrEmpty()) {
            binding.prodWeightView.visibility = View.GONE
        } else {
            binding.prodWeightView.visibility = View.VISIBLE
        }
        if (columnsData.priceStatus.isNullOrEmpty()) {
            binding.prodPriceStatusView.visibility = View.GONE
        } else {
            binding.prodPriceStatusView.visibility = View.VISIBLE
        }
        if (columnsData.age.isNullOrEmpty()) {
            binding.prodAgeView.visibility = View.GONE
        } else {
            binding.prodAgeView.visibility = View.VISIBLE
        }
        if (columnsData.productType.isNullOrEmpty()) {
            binding.prodTypeView.visibility = View.GONE
        } else {
            binding.prodTypeView.visibility = View.VISIBLE
        }
        if (columnsData.chickType.isNullOrEmpty()) {
            binding.prodChickTypeView.visibility = View.GONE
        } else {
            binding.prodChickTypeView.visibility = View.VISIBLE
        }
        if (columnsData.weightContainer.isNullOrEmpty()) {
            binding.prodWeightContainerView.visibility = View.GONE
        } else {
            binding.prodWeightContainerView.visibility = View.VISIBLE
        }
        if (columnsData.feed.isNullOrEmpty()) {
            binding.prodFeedDateView.visibility = View.GONE
        } else {
            binding.prodFeedDateView.visibility = View.VISIBLE
        }
        binding.companyListener = companyListener
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