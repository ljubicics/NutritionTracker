package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.models.response.AllMealsForCategoryResponse
import rs.raf.nutritiontracker.data.models.response.OneMealForCategoryResponse

sealed class MealsForAreaState {
    object Loading: MealsForAreaState()
    object DataFetched: MealsForAreaState()
    data class Success(val mealsForCategory: List<OneMealForCategoryResponse>): MealsForAreaState()
    data class Error(val message: String): MealsForAreaState()
}