package com.example.flexify.ui.info.Food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flexify.R
import com.example.flexify.data.dbModel.Food

class FoodAdapter  : ListAdapter<Food, FoodAdapter.FoodViewHolder>(FoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food_in_details, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.name)
        private val caloriesTextView: TextView = itemView.findViewById(R.id.cal)

        fun bind(food: Food) {
            nameTextView.text = food.name
            caloriesTextView.text = food.calories.toString()
        }
    }
}

class FoodDiffCallback : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean = oldItem == newItem
}