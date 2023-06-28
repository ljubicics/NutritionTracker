package rs.raf.nutritiontracker.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.databinding.LayoutItemCategoryBinding

class MealForCategoryViewHolder(
    private val itemBinding: LayoutItemCategoryBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(mealForCategory: MealForCategory) {
        Picasso
            .get()
            .load(mealForCategory.strMealThumb)
            .into(itemBinding.categoryIV)
        itemBinding.categoryNameTV.text = mealForCategory.strMeal
    }
}