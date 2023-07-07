package rs.raf.nutritiontracker.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.data.models.ShortMeal

class SavedMealDiffCallback : DiffUtil.ItemCallback<SavedMeal>(){
    override fun areItemsTheSame(oldItem: SavedMeal, newItem: SavedMeal): Boolean {
        return oldItem.strMeal == newItem.strMeal
    }

    override fun areContentsTheSame(oldItem: SavedMeal, newItem: SavedMeal): Boolean {
        return oldItem.strMeal == newItem.strMeal
    }
}