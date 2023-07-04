package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.presentation.view.states.MealsForIngredientState

interface MealsForIngredientContract {

    interface ViewModel {

        val mealsForIngredientState: LiveData<MealsForIngredientState>

        fun fetchAllMealsForIngredient(ingredient: String)
    }
}