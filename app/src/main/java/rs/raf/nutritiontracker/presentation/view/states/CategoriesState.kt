package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.data.models.Meal

sealed class CategoriesState {
    object Loading: CategoriesState()
    object DataFetched: CategoriesState()
    data class Success(val categories: List<Category>): CategoriesState()
    data class Error(val message: String): CategoriesState()
}