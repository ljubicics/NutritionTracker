package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.models.MealForCategory

sealed class MealsForCategoryState {
    object Loading: MealsForCategoryState()
    object DataFetched: MealsForCategoryState()
    data class Success(val mealsForCategory: List<MealForCategory>): MealsForCategoryState()
    data class Error(val message: String): MealsForCategoryState()
}