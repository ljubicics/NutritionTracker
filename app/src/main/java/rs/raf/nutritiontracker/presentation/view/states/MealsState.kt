package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.models.Meal

sealed class MealsState {
    object Loading: MealsState()
    object DataFetched: MealsState()
    data class Success(val meals: List<Meal>): MealsState()
    data class Error(val message: String): MealsState()
}