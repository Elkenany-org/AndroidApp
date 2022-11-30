//package com.elkenany.views.home.home_sector.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.elkenany.ClickListener
//import com.elkenany.databinding.SmallRecyclerItemBinding
//import com.elkenany.entities.home_data.SectorsLogo
//
//class SectorsPartnerAdapter(private val partnerClickListener: ClickListener<SectorsLogo>) :
//    ListAdapter<SectorsLogo, SectorsPartnerViewHolder>(
//        SectorsPartnerDiffUtil()
//    ) {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectorsPartnerViewHolder {
//        return SectorsPartnerViewHolder.from(parent)
//    }
//
//    override fun onBindViewHolder(holder: SectorsPartnerViewHolder, position: Int) {
//        holder.bind(getItem(position)!!, partnerClickListener)
//    }
//
//}
//
//class SectorsPartnerViewHolder private constructor(private val binding: SmallRecyclerItemBinding) :
//    RecyclerView.ViewHolder(binding.root) {
//
//    fun bind(logo: SectorsLogo, partnerClickListener: ClickListener<SectorsLogo>) {
//        binding.data = logo
//        binding.name = ""
//        binding.image = logo.image
//        binding.clickListener = partnerClickListener
//        binding.executePendingBindings()
//    }
//
//    companion object {
//        fun from(parent: ViewGroup): SectorsPartnerViewHolder {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = SmallRecyclerItemBinding.inflate(layoutInflater, parent, false)
//            return SectorsPartnerViewHolder(binding)
//        }
//    }
//}
//
//
//class SectorsPartnerDiffUtil : DiffUtil.ItemCallback<SectorsLogo>() {
//    override fun areItemsTheSame(oldItem: SectorsLogo, newItem: SectorsLogo): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: SectorsLogo, newItem: SectorsLogo): Boolean {
//        return oldItem == newItem
//    }
//}
//
