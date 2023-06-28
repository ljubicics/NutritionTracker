package rs.raf.nutritiontracker.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.ShortMeal

class ShortMealDiffCallback : DiffUtil.ItemCallback<ShortMeal>() {
    override fun areItemsTheSame(oldItem: ShortMeal, newItem: ShortMeal): Boolean {
        return oldItem.strMeal == newItem.strMeal
    }

    override fun areContentsTheSame(oldItem: ShortMeal, newItem: ShortMeal): Boolean {
        return oldItem.strMeal == newItem.strMeal
    }
}