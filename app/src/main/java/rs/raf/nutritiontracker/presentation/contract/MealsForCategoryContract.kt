package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.presentation.view.states.AddCategoryState
import rs.raf.nutritiontracker.presentation.view.states.AddMealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState

interface MealsForCategoryContract {

    interface ViewModel {
        val mealsForCategoryState: LiveData<MealsForCategoryState>
        val addMealsForCategoryDone: LiveData<AddMealsForCategoryState>

        fun getAllMeals()
        fun fetchAllMealsForCategory()
        fun getAllMealsForCategory(categoryName: String)
        fun getAllMealsByName(mealName: String)
    }
}