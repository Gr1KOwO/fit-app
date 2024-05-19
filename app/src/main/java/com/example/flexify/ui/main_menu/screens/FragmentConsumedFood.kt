package com.example.flexify.ui.main_menu.screens

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flexify.data.dbModel.ConsumedFoodEntity
import com.example.flexify.data.dbModel.Dish
import com.example.flexify.data.dbModel.Food
import com.example.flexify.databinding.FragmentConsumedFoodBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class FragmentConsumedFood : Fragment() {
    private lateinit var binding: FragmentConsumedFoodBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val consumedViewModel by createViewModelLazy(
        ConsumedViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory }
    )

    private lateinit var consumedFoodAdapter: ConsumedFoodAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConsumedFoodBinding.inflate(inflater, container, false)
        // Observe data
        consumedViewModel.dishes.observe(viewLifecycleOwner, Observer { dishes ->
            updateDishList(dishes)
        })

        consumedViewModel.foods.observe(viewLifecycleOwner, Observer { foods ->
            updateFoodList(foods)
        })

        consumedViewModel.consumedFood.observe(viewLifecycleOwner, Observer { consumedFood ->
            consumedFoodAdapter.submitList(consumedFood)
        })

        consumedViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        consumedFoodAdapter = ConsumedFoodAdapter(
            onEditClick = { consumedFoodEntity -> editConsumedFood(consumedFoodEntity) },
            onDeleteClick = { consumedFoodEntity -> deleteConsumedFood(consumedFoodEntity) },
            getFoodName = { id -> consumedViewModel.getFoodNameById(id) ?: "UNKNOWN" },
            getDishName = { id -> consumedViewModel.getDishNameById(id) ?: "UNKNOWN" }
        )
        binding.consumedFoodRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = consumedFoodAdapter
        }


        binding.addButton.setOnClickListener {
            val selectedDish = binding.dishSpinner.selectedItem as? Dish
            val selectedFood = binding.foodSpinner.selectedItem as? Food
            val quantity = binding.quantityEditText.text.toString().toIntOrNull() ?: 0

            if (selectedDish == null && selectedFood == null) {
                Toast.makeText(requireContext(), "Выберите пожалуйста блюдо или еду", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(quantity<=0){
                Toast.makeText(requireContext(), "Введите количество больше 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val calories = (selectedDish?.calories ?: selectedFood?.calories ?: 0f) * quantity
            consumedViewModel.addConsumedFood(
                selectedFood?.id,
                selectedDish?.id,
                quantity,
                calories
            )

            binding.quantityEditText.text.clear()
            binding.dishSpinner.setSelection(0)
            binding.foodSpinner.setSelection(0)
        }
    }

    private fun updateDishList(dishes: List<Dish>) {
        val dishList = listOf<Dish?>(null) + dishes
        val adapter = DishFoodAdapter(requireContext(), dishList)
        binding.dishSpinner.adapter = adapter
    }

    private fun updateFoodList(foods: List<Food>) {
        val foodList = listOf<Food?>(null) + foods
        val adapter = DishFoodAdapter(requireContext(), foodList)
        binding.foodSpinner.adapter = adapter
    }

    private fun editConsumedFood(consumedFoodEntity: ConsumedFoodEntity) {
        // Показать диалог редактирования
        val dialogView = LayoutInflater.from(context).inflate(com.example.flexify.R.layout.dialog_edit_consumed_food, null)
        val quantityEditText = dialogView.findViewById<EditText>(com.example.flexify.R.id.editQuantityEditText)

        quantityEditText.setText(consumedFoodEntity.quantity.toString())

        AlertDialog.Builder(context)
            .setTitle("Edit Consumed Food")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newQuantity = quantityEditText.text.toString().toIntOrNull() ?: consumedFoodEntity.quantity
                val newCalories = (newQuantity / consumedFoodEntity.quantity.toFloat()) * consumedFoodEntity.calories

                consumedViewModel.updateConsumedFood(consumedFoodEntity.copy(quantity = newQuantity, calories = newCalories))
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun deleteConsumedFood(consumedFoodEntity: ConsumedFoodEntity) {
        consumedViewModel.deleteConsumedFood(consumedFoodEntity)
    }
}
