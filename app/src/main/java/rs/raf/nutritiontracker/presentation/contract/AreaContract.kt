package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.presentation.view.states.AddCategoryState
import rs.raf.nutritiontracker.presentation.view.states.AreaState
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState

interface AreaContract {
    interface ViewModel {
        val areaState: LiveData<AreaState>

        fun fetchAllAreas()
        fun getAllAreas()
    }
}