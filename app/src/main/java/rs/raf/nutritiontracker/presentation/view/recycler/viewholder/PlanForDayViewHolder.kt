package rs.raf.nutritiontracker.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.nutritiontracker.data.models.MealForPlan
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.databinding.LayoutItemMealForDayBinding
import java.io.File

class PlanForDayViewHolder(
    private val itemBinding: LayoutItemMealForDayBinding,
    var onItemDeleteClicked: (Int) -> Unit,
    var listener: (Int) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root){
    init {
        itemBinding.dayMealDeleteIB.setOnClickListener{
            onItemDeleteClicked.invoke(bindingAdapterPosition)
        }
        itemView.setOnClickListener{
            listener.invoke(bindingAdapterPosition)
        }
    }

    fun bind(savedMeal: MealForPlan) {
        if(savedMeal.strMealThumb?.contains("http") == true) {
            Picasso
                .get()
                .load(savedMeal.strMealThumb)
                .into(itemBinding.dayMealIV)
        } else {
            Picasso
                .get()
                .load(File(savedMeal.strMealThumb))
                .into(itemBinding.dayMealIV)
        }
        itemBinding.dayMealTV.text = savedMeal.strMeal
        itemBinding.mealTypeTV.text = savedMeal.mealType
    }
}