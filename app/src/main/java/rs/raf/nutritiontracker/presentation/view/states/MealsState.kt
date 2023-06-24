package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.models.Meal

// State klase se ticu dohvatanja iz baze
sealed class MealsState {
    object Loading: MealsState()
    //Ovo je medjustanje dohvatanja podataka sa apija dok ne stignu u bazu
    object DataFetched: MealsState()
    data class Success(val meals: List<Meal>): MealsState()
    data class Error(val message: String): MealsState()
}