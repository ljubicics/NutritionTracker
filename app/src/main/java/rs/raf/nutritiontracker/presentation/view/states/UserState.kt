package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.User

sealed class UserState {
    object Loading: UserState()
    //Ovo je medjustanje dohvatanja podataka sa apija dok ne stignu u bazu
    object DataFetched: UserState()
    data class Success(val users: List<User>): UserState()
    data class Error(val message: String): UserState()
}