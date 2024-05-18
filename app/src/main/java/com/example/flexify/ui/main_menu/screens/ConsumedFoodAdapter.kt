package com.example.flexify.ui.main_menu.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flexify.data.dbModel.ConsumedFoodEntity
import com.example.flexify.databinding.ItemConsumedFoodBinding

class ConsumedFoodAdapter(
    private val onEditClick: (ConsumedFoodEntity) -> Unit,
    private val onDeleteClick: (ConsumedFoodEntity) -> Unit,
    private val getFoodName: (Int) -> String?,
    private val getDishName: (Int) -> String?
) : ListAdapter<ConsumedFoodEntity, ConsumedFoodAdapter.ConsumedFoodViewHolder>(ConsumedFoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsumedFoodViewHolder {
        val binding = ItemConsumedFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConsumedFoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConsumedFoodViewHolder, position: Int) {
        val consumedFoodEntity = getItem(position)
        holder.bind(consumedFoodEntity, onEditClick, onDeleteClick, getFoodName, getDishName)
    }

    class ConsumedFoodViewHolder(private val binding: ItemConsumedFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            consumedFoodEntity: ConsumedFoodEntity,
            onEditClick: (ConsumedFoodEntity) -> Unit,
            onDeleteClick: (ConsumedFoodEntity) -> Unit,
            getFoodName: (Int) -> String?,
            getDishName: (Int) -> String?
        ) {
            binding.consumedFoodName.text = consumedFoodEntity.foodId?.let { getFoodName(it) } ?: consumedFoodEntity.dishId?.let { getDishName(it) } ?: "UNKNOWN"
            binding.consumedFoodQuantity.text = consumedFoodEntity.quantity.toString()
            binding.consumedFoodCalories.text = consumedFoodEntity.calories.toString()

            binding.editButton.setOnClickListener { onEditClick(consumedFoodEntity) }
            binding.deleteButton.setOnClickListener { onDeleteClick(consumedFoodEntity) }
        }
    }
}

class ConsumedFoodDiffCallback : DiffUtil.ItemCallback<ConsumedFoodEntity>() {
    override fun areItemsTheSame(oldItem: ConsumedFoodEntity, newItem: ConsumedFoodEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ConsumedFoodEntity, newItem: ConsumedFoodEntity): Boolean {
        return oldItem == newItem
    }
}
