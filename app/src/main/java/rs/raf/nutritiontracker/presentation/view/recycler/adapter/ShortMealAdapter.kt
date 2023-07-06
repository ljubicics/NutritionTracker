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

class ShortMealAdapter(
    var onItemMoreClicked: (ShortMeal) -> Unit,
    var listener: (ShortMeal) -> Unit
) : ListAdapter<ShortMeal, ShortMealViewHolder>(
    ShortMealDiffCallback()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortMealViewHolder {
        val itemBinding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShortMealViewHolder(itemBinding, onItemMoreClicked = {
            val shortMeal: ShortMeal = getItem(it)
            onItemMoreClicked.invoke(shortMeal)
        }, listener = {
            val shortMeal: ShortMeal = getItem(it)
            listener.invoke(shortMeal)
        })
    }

    override fun onBindViewHolder(holder: ShortMealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}