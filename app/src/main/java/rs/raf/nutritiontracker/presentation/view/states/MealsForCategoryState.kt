package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.models.Category

sealed class MealsForCategoryState {
    object Loading: MealsForCategoryState()
    object DataFetched: MealsForCategoryState()
    data class Success(val categories: List<Category>): MealsForCategoryState()
    data class Error(val message: String): MealsForCategoryState()
}