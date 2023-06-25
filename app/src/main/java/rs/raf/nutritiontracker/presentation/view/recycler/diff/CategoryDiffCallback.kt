package rs.raf.nutritiontracker.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.nutritiontracker.data.models.Category

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.strCategory == newItem.strCategory
    }
}