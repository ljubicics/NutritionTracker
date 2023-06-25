package rs.raf.nutritiontracker.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.databinding.LayoutItemCategoryBinding
import rs.raf.nutritiontracker.presentation.view.fragments.CategoryDialogFragment
import rs.raf.nutritiontracker.presentation.view.recycler.diff.CategoryDiffCallback
import rs.raf.nutritiontracker.presentation.view.recycler.viewholder.CategoryViewHolder

class CategoryAdapter(
    var onItemMoreClicked: (Category) -> Unit,
    var listener: (Category) -> Unit
) : ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding, onItemMoreClicked = {
            val category: Category = getItem(it)
            onItemMoreClicked.invoke(category)
        }, listener = {
            val category: Category = getItem(it)
            listener.invoke(category)
        })
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}