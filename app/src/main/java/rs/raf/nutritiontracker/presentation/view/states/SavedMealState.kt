package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.models.entities.MealSavedEntity

sealed class SavedMealState {
    object Loading: SavedMealState()
    object DataFetched: SavedMealState()
    data class Success(val savedMeals: List<MealSavedEntity>): SavedMealState()
    data class Error(val message: String): SavedMealState()
}