package rs.raf.nutritiontracker.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.databinding.LayoutItemCategoryBinding
import rs.raf.nutritiontracker.databinding.LayoutItemMealBinding
import rs.raf.nutritiontracker.presentation.view.recycler.diff.MealForCategoryDiffCallback
import rs.raf.nutritiontracker.presentation.view.recycler.viewholder.MealForCategoryViewHolder

class MealForCategoryAdapter() : ListAdapter<MealForCategory, MealForCategoryViewHolder>(MealForCategoryDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealForCategoryViewHolder {
        val itemBinding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealForCategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MealForCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}