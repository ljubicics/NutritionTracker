package rs.raf.nutritiontracker.presentation.view.states

sealed class AddMealState {
    object Success: AddMealState()
    data class Error(val message: String): AddMealState()
}