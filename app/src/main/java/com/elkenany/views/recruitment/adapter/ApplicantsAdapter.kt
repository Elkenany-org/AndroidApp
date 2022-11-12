package com.elkenany.views.recruitment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkenany.ClickListener
import com.elkenany.databinding.ApplicantsCardItemBinding
import com.elkenany.entities.recruitment.Applicant


class ApplicantsAdapter(
    private val clickListener: ClickListener<Applicant>,
) :
    ListAdapter<Applicant, ApplicantAdapterViewHolder>(
        ApplicantsAdapterDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ApplicantAdapterViewHolder {
        return ApplicantAdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ApplicantAdapterViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

}

class ApplicantAdapterViewHolder private constructor(private val binding: ApplicantsCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: Applicant,
        clickListener: ClickListener<Applicant>,
    ) {
        binding.data = data
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ApplicantAdapterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ApplicantsCardItemBinding.inflate(layoutInflater, parent, false)
            return ApplicantAdapterViewHolder(binding)
        }
    }
}


class ApplicantsAdapterDiffUtil : DiffUtil.ItemCallback<Applicant>() {
    override fun areItemsTheSame(oldItem: Applicant, newItem: Applicant): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Applicant, newItem: Applicant): Boolean {
        return oldItem == newItem
    }
}