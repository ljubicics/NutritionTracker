package rs.raf.nutritiontracker.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.nutritiontracker.data.models.MealForCategory

class MealForCategoryDiffCallback : DiffUtil.ItemCallback<MealForCategory>() {
    override fun areItemsTheSame(oldItem: MealForCategory, newItem: MealForCategory): Boolean {
        return oldItem.strMeal == newItem.strMeal
    }

    override fun areContentsTheSame(oldItem: MealForCategory, newItem: MealForCategory): Boolean {
        return oldItem.strMeal == newItem.strMeal
    }
}