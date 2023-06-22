package rs.raf.nutritiontracker.presentation.view.states

sealed class AddCategoryState {
    object Success: AddCategoryState()
    data class Error(val message: String): AddCategoryState()
}