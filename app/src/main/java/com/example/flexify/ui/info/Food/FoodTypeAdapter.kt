package com.example.flexify.ui.info.Food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flexify.R
import com.example.flexify.data.dbModel.FoodType

class FoodTypeAdapter(
    private val onItemClick: (FoodType) -> Unit
) : ListAdapter<FoodType, FoodTypeAdapter.FoodTypeViewHolder>(FoodTypeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodTypeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food_type, parent, false)
        return FoodTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodTypeViewHolder, position: Int) {
        val foodType = getItem(position)
        holder.bind(foodType, onItemClick)
    }

    class FoodTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.title)

        fun bind(foodType: FoodType, onItemClick: (FoodType) -> Unit) {
            textView.text = foodType.name
            itemView.setOnClickListener { onItemClick(foodType) }
        }
    }
}

class FoodTypeDiffCallback : DiffUtil.ItemCallback<FoodType>() {
    override fun areItemsTheSame(oldItem: FoodType, newItem: FoodType): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: FoodType, newItem: FoodType): Boolean = oldItem == newItem
}