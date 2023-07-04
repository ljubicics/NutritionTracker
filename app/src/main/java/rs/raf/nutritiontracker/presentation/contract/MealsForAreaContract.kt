package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.presentation.view.states.AddMealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealsForAreaState

interface MealsForAreaContract {

    interface ViewModel {

        val mealsForAreaState: LiveData<MealsForAreaState>
        fun fetchAllMealsForArea(area: String)

    }

}