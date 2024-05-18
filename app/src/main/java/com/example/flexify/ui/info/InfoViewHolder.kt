package com.example.flexify.ui.info

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.flexify.databinding.ItemInfoBinding

class InfoViewHolder(private val binding: ItemInfoBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(infoItem: InfoItem) {
        binding.Preview.setImageDrawable(infoItem.img)
        binding.text.text = infoItem.titleText
    }
}