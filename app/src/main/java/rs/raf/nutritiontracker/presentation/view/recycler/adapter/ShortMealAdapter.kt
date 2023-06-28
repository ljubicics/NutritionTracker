package rs.raf.nutritiontracker.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.ShortMeal
import rs.raf.nutritiontracker.databinding.LayoutItemCategoryBinding
import rs.raf.nutritiontracker.presentation.view.recycler.diff.MealForCategoryDiffCallback
import rs.raf.nutritiontracker.presentation.view.recycler.diff.ShortMealDiffCallback
import rs.raf.nutritiontracker.presentation.view.recycler.viewholder.MealForCategoryViewHolder
import rs.raf.nutritiontracker.presentation.view.recycler.viewholder.ShortMealViewHolder

class ShortMealAdapter() : ListAdapter<ShortMeal, ShortMealViewHolder>(
    ShortMealDiffCallback()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortMealViewHolder {
        val itemBinding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShortMealViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ShortMealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}