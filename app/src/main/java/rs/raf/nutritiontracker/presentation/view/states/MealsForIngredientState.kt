package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.response.OneMealForCategoryResponse

sealed class MealsForIngredientState {
    object Loading: MealsForIngredientState()
    object DataFetched: MealsForIngredientState()
    data class Success(val mealsForCategory: List<OneMealForCategoryResponse>): MealsForIngredientState()
    data class Error(val message: String): MealsForIngredientState()
}