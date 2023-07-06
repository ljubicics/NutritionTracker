package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.presentation.view.states.AddMealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.AreaState
import rs.raf.nutritiontracker.presentation.view.states.MealsForAreaState

interface MealsForAreaContract {

    interface ViewModel {

        val mealsForAreaState: LiveData<MealsForAreaState>
        val areaState: LiveData<AreaState>

        fun fetchAllAreas()
        fun getAllAreas()
        fun fetchAllMealsForArea(area: String)

    }

}