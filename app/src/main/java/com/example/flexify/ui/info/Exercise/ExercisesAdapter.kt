package com.example.flexify.ui.info.Exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flexify.R
import com.example.flexify.data.dbModel.Exercise

class ExercisesAdapter (private val onItemClick: (Exercise) -> Unit
) : ListAdapter<Exercise, ExercisesAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise, onItemClick)
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.title)

        fun bind(exercise: Exercise, onItemClick: (Exercise) -> Unit) {
            textView.text = exercise.name
            itemView.setOnClickListener { onItemClick(exercise) }
        }
    }
}

class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean = oldItem == newItem
}