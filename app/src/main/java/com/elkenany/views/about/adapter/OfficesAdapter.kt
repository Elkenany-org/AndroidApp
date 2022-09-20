package com.elkenany.views.about.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.AboutBranchItemCardBinding
import com.elkenany.entities.home_data.Office

class OfficesAdapter(
    private val call: ClickListener<Office>,
    private val mail: ClickListener<Office>,
    private val locate: ClickListener<Office>,
) :
    ListAdapter<Office, OfficeViewHolder>(
        OfficeAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OfficeViewHolder {
        return OfficeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OfficeViewHolder, position: Int) {
        holder.bind(getItem(position)!!, call, mail, locate)
    }

}

class OfficeViewHolder private constructor(private val binding: AboutBranchItemCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        office: Office,
        call: ClickListener<Office>,
        mail: ClickListener<Office>,
        locate: ClickListener<Office>,
    ) {
        binding.data = office
        binding.phoneTv.setOnClickListener {
            call.onClick(office)
        }
        binding.mobileTv.setOnClickListener {
            call.onClick(office)
        }
        binding.faxTv.setOnClickListener {
            call.onClick(office)
        }
        binding.emailTv.setOnClickListener {
            mail.onClick(office)
        }
        binding.locationTv.setOnClickListener {
            locate.onClick(office)
        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): OfficeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AboutBranchItemCardBinding.inflate(layoutInflater, parent, false)
            return OfficeViewHolder(binding)
        }
    }
}


class OfficeAdapterDiffUtil : DiffUtil.ItemCallback<Office>() {
    override fun areItemsTheSame(
        oldItem: Office,
        newItem: Office,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Office,
        newItem: Office,
    ): Boolean {
        return oldItem == newItem
    }
}