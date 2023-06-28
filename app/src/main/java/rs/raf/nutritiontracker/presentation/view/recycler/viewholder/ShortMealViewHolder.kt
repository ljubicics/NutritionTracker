package rs.raf.nutritiontracker.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.ShortMeal
import rs.raf.nutritiontracker.databinding.LayoutItemCategoryBinding

class ShortMealViewHolder(
    private val itemBinding: LayoutItemCategoryBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(shortMeal: ShortMeal) {
        Picasso
            .get()
            .load(shortMeal.strMealThumb)
            .into(itemBinding.categoryIV)
        itemBinding.categoryNameTV.text = shortMeal.strMeal
    }
}