package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.data.models.DayInTheWeek
import rs.raf.nutritiontracker.data.models.MealForPlan
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.SavedMealState

interface PickMealsContract {
    interface ViewModel {
        val remoteMealsState: LiveData<MealsForCategoryState>
        val localMealsState: LiveData<SavedMealState>
        val number: LiveData<Long>
        val mondayState: LiveData<List<MealForPlan>>
        val tuesdayState: LiveData<List<MealForPlan>>
        val wednesdayState: LiveData<List<MealForPlan>>
        val thursdayState: LiveData<List<MealForPlan>>
        val fridayState: LiveData<List<MealForPlan>>
        val saturdayState: LiveData<List<MealForPlan>>
        val sundayState: LiveData<List<MealForPlan>>

        fun addMealToDay(meal: MealForPlan, day: DayInTheWeek)
        fun removeMealFromDay(id: Long, day: DayInTheWeek)
        fun increaseNumber()
        fun getAllMealsRemote()
        fun getAllMealsLocal(username: String)

    }
}