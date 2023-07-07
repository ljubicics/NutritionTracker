package rs.raf.nutritiontracker.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.databinding.LayoutSavedMealItemBinding
import rs.raf.nutritiontracker.presentation.view.recycler.diff.SavedMealDiffCallback
import rs.raf.nutritiontracker.presentation.view.recycler.viewholder.SavedMealViewHolder

class SavedMealAdapter(
    var onItemEditClicked: (SavedMeal) -> Unit,
    var onItemDeleteClicked: (SavedMeal) -> Unit,
    var listener: (SavedMeal) -> Unit
) : ListAdapter<SavedMeal, SavedMealViewHolder>(
    SavedMealDiffCallback()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMealViewHolder {
        val itemBinding = LayoutSavedMealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedMealViewHolder(itemBinding, onItemDeleteClicked = {
            val savedMeal: SavedMeal = getItem(it)
            onItemDeleteClicked.invoke(savedMeal)
        }, onItemEditClicked = {
            val savedMeal: SavedMeal = getItem(it)
            onItemEditClicked.invoke(savedMeal)
        }, listener = {
            val savedMeal: SavedMeal = getItem(it)
            listener.invoke(savedMeal)
        })
    }

    override fun onBindViewHolder(holder: SavedMealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}