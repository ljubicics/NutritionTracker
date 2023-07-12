package rs.raf.nutritiontracker.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.nutritiontracker.data.models.MealForPlan
import rs.raf.nutritiontracker.data.models.SavedMeal

class MealForPlanDiffcallback : DiffUtil.ItemCallback<MealForPlan>(){
    override fun areItemsTheSame(oldItem: MealForPlan, newItem: MealForPlan): Boolean {
        return oldItem.strMeal == newItem.strMeal
    }

    override fun areContentsTheSame(oldItem: MealForPlan, newItem: MealForPlan): Boolean {
        return oldItem.strMeal == newItem.strMeal
    }
}