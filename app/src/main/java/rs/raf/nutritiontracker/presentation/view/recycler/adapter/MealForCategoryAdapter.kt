package rs.raf.nutritiontracker.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.databinding.LayoutItemCategoryBinding
import rs.raf.nutritiontracker.presentation.view.recycler.diff.MealForCategoryDiffCallback
import rs.raf.nutritiontracker.presentation.view.recycler.viewholder.MealForCategoryViewHolder

class MealForCategoryAdapter(
    var onItemMoreClicked: (MealForCategory) -> Unit,
    var listener: (MealForCategory) -> Unit
) : ListAdapter<MealForCategory, MealForCategoryViewHolder>(MealForCategoryDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealForCategoryViewHolder {
        val itemBinding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealForCategoryViewHolder(itemBinding, onItemMoreClicked = {
            val mealsForCategory: MealForCategory = getItem(it)
            onItemMoreClicked.invoke(mealsForCategory)
        }, listener = {
            val mealForCategory: MealForCategory = getItem(it)
            listener.invoke(mealForCategory)
        })
    }

    override fun onBindViewHolder(holder: MealForCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}