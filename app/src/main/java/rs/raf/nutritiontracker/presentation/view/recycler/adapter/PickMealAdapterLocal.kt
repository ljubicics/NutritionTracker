package rs.raf.nutritiontracker.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.databinding.LayoutPickMealBinding
import rs.raf.nutritiontracker.presentation.view.recycler.diff.SavedMealDiffCallback
import rs.raf.nutritiontracker.presentation.view.recycler.viewholder.PickMealLocalViewHolder
import rs.raf.nutritiontracker.presentation.view.recycler.viewholder.SavedMealViewHolder

class PickMealAdapterLocal(
    var onItemAddClicked: (SavedMeal) -> Unit,
    var listener: (SavedMeal) -> Unit
) : ListAdapter<SavedMeal, PickMealLocalViewHolder>(
    SavedMealDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickMealLocalViewHolder {
        val itemBinding = LayoutPickMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PickMealLocalViewHolder(itemBinding, onItemAddClicked = {
            val savedMeal: SavedMeal = getItem(it)
            onItemAddClicked.invoke(savedMeal)
        }, listener = {
            val savedMeal: SavedMeal = getItem(it)
            listener.invoke(savedMeal)
        })
    }

    override fun onBindViewHolder(holder: PickMealLocalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}