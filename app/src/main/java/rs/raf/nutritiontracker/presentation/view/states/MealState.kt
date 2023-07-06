package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.models.Meal

// State klase se ticu dohvatanja iz baze
sealed class MealState {
    object Loading: MealState()
    //Ovo je medjustanje dohvatanja podataka sa apija dok ne stignu u bazu
    object DataFetched: MealState()
    data class Success(val meals: List<Meal>): MealState()
    data class Error(val message: String): MealState()
}