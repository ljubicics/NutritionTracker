package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.data.models.DayInTheWeek
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.SavedMealState

interface PickMealsContract {
    interface ViewModel {
        val remoteMealsState: LiveData<MealsForCategoryState>
        val localMealsState: LiveData<SavedMealState>
        val mondayState: LiveData<List<SavedMeal>>
        val tuesdayState: LiveData<List<SavedMeal>>
        val wednesdayState: LiveData<List<SavedMeal>>
        val thursdayState: LiveData<List<SavedMeal>>
        val fridayState: LiveData<List<SavedMeal>>
        val saturdayState: LiveData<List<SavedMeal>>
        val sundayState: LiveData<List<SavedMeal>>

        fun addMealToDay(meal: SavedMeal, day: DayInTheWeek)
        fun removeMealFromDay(id: Long, day: DayInTheWeek)

        fun getAllMealsRemote()
        fun getAllMealsLocal(username: String)

    }
}