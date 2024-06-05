package com.example.flexify.ui.Dishes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flexify.R
import com.example.flexify.data.dbModel.Dish

class DishesAdapter (private val onItemClick: (Dish) -> Unit
) : ListAdapter<Dish, DishesAdapter.DishViewHolder>(DishDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise, onItemClick)
    }

    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.title)

        fun bind(dish: Dish, onItemClick: (Dish) -> Unit) {
            textView.text = dish.name
            itemView.setOnClickListener { onItemClick(dish) }
        }
    }
}

class DishDiffCallback : DiffUtil.ItemCallback<Dish>() {
    override fun areItemsTheSame(oldItem: Dish, newItem: Dish): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Dish, newItem: Dish): Boolean = oldItem == newItem
}