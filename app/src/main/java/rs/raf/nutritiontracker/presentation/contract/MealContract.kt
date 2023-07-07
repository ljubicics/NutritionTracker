package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.entities.MealEntity
import rs.raf.nutritiontracker.data.models.entities.MealSavedEntity
import rs.raf.nutritiontracker.presentation.view.states.AddMealState
import rs.raf.nutritiontracker.presentation.view.states.MealState

interface MealContract {
    interface ViewModel {
        val mealsState: LiveData<MealState>
        val addMealDone: LiveData<AddMealState>

        fun fetchAllMeals()
        fun fetchAllMealsForCategory(category: String)
        fun fetchAllMealsByName(name: String)
        fun fetchMealById(id: String)
        fun getAllMeals()
        fun getMealsByName(name: String)
        fun addMeal(meal: MealSavedEntity)
    }
}