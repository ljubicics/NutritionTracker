package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.entities.UserEntity
import rs.raf.nutritiontracker.presentation.view.states.AddCategoryState
import rs.raf.nutritiontracker.presentation.view.states.AddMealState
import rs.raf.nutritiontracker.presentation.view.states.AddUserState
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import rs.raf.nutritiontracker.presentation.view.states.MealsState
import rs.raf.nutritiontracker.presentation.view.states.UserState

interface UserContract {
    interface ViewModel {
        val userState: LiveData<UserState>
        val addUserState: LiveData<AddUserState>

        fun getUserByUsername(username: String)
        fun insertUser(user: UserEntity)
    }
}