package rs.raf.nutritiontracker.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.nutritiontracker.data.models.MealForPlan
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.databinding.LayoutItemMealForDayBinding
import rs.raf.nutritiontracker.presentation.view.recycler.diff.MealForPlanDiffcallback
import rs.raf.nutritiontracker.presentation.view.recycler.diff.SavedMealDiffCallback
import rs.raf.nutritiontracker.presentation.view.recycler.viewholder.PlanForDayViewHolder

class MealsForDayAdapter(
    var onItemDeleteClicked: (MealForPlan) -> Unit,
    var listener: (MealForPlan) -> Unit
) : ListAdapter<MealForPlan, PlanForDayViewHolder>(
    MealForPlanDiffcallback()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanForDayViewHolder {
        val itemBinding = LayoutItemMealForDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanForDayViewHolder(itemBinding, onItemDeleteClicked = {
            val mealForPlan: MealForPlan = getItem(it)
            onItemDeleteClicked.invoke(mealForPlan)
        }, listener = {
            val mealForPlan: MealForPlan = getItem(it)
            listener.invoke(mealForPlan)
        })
    }

    override fun onBindViewHolder(holder: PlanForDayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}