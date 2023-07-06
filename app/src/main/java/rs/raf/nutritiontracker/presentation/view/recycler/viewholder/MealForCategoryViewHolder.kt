package rs.raf.nutritiontracker.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.databinding.LayoutItemCategoryBinding

class MealForCategoryViewHolder(
    private val itemBinding: LayoutItemCategoryBinding,
    var onItemMoreClicked: (Int) -> Unit,
    var listener: (Int) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    init {
        // ovde stavljam listener samo na konktretnu sliku u view-u koja se binduje
        itemBinding.moreAboutCatIB.setOnClickListener{
            onItemMoreClicked.invoke(bindingAdapterPosition)
        }

        // ovde stavljam listener na celu View komponentu
        itemView.setOnClickListener{
            listener.invoke(bindingAdapterPosition)
        }
    }

    fun bind(mealForCategory: MealForCategory) {
        Picasso
            .get()
            .load(mealForCategory.strMealThumb)
            .into(itemBinding.categoryIV)
        itemBinding.categoryNameTV.text = mealForCategory.strMeal
    }
}