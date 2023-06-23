package rs.raf.nutritiontracker.presentation.view.states

sealed class AddMealsForCategoryState {
    object Success: AddMealsForCategoryState()
    data class Error(val message: String): AddMealsForCategoryState()
}