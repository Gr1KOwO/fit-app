package com.example.flexify.ui.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.flexify.databinding.ItemInfoBinding

class InfoAdapter(
    private val onClick: (titleText: String) -> Unit,
): ListAdapter<InfoItem,InfoViewHolder>(ProductDiffCallback()) {

    class ProductDiffCallback : DiffUtil.ItemCallback<InfoItem>() {
        override fun areItemsTheSame(oldItem: InfoItem, newItem: InfoItem): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: InfoItem, newItem: InfoItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInfoBinding.inflate(inflater, parent, false)
        return InfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val infoItem = getItem(position)
        holder.itemView.setOnClickListener{ onClick(infoItem.titleText) }
        holder.bind(infoItem)
    }
}