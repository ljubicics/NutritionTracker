package rs.raf.nutritiontracker.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.databinding.LayoutPickMealBinding
import rs.raf.nutritiontracker.presentation.view.recycler.diff.MealForCategoryDiffCallback
import rs.raf.nutritiontracker.presentation.view.recycler.diff.SavedMealDiffCallback
import rs.raf.nutritiontracker.presentation.view.recycler.viewholder.PickMealRemoteViewHolder

class PickMealAdapterRemote(
    var onItemAddClicked: (MealForCategory) -> Unit,
    var listener: (MealForCategory) -> Unit
) : ListAdapter<MealForCategory, PickMealRemoteViewHolder>(
    MealForCategoryDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickMealRemoteViewHolder {
        val itemBinding = LayoutPickMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PickMealRemoteViewHolder(itemBinding, onItemAddClicked = {
            val savedMeal: MealForCategory = getItem(it)
            onItemAddClicked.invoke(savedMeal)
        }, listener = {
            val savedMeal: MealForCategory = getItem(it)
            listener.invoke(savedMeal)
        })
    }

    override fun onBindViewHolder(holder: PickMealRemoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
