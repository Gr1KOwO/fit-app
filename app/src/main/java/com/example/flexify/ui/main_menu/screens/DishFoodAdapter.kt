package com.example.flexify.ui.main_menu.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.flexify.data.dbModel.Dish
import com.example.flexify.data.dbModel.Food

class DishFoodAdapter<T>(context: Context, objects: List<T?>) :
    ArrayAdapter<T?>(context, android.R.layout.simple_spinner_item, objects) {

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(
            position,
            convertView,
            parent,
            android.R.layout.simple_spinner_item
        )
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(
            position,
            convertView,
            parent,
            android.R.layout.simple_spinner_dropdown_item
        )
    }

    private fun createViewFromResource(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
        resource: Int
    ): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val text = view.findViewById<TextView>(android.R.id.text1)
        val item = getItem(position)
        text.text = when (item) {
            is Dish -> item.name
            is Food -> item.name
            else -> ""
        }
        return view
    }
}