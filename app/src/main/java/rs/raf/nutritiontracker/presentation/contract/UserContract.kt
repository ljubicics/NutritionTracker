package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.data.models.entities.UserEntity
import rs.raf.nutritiontracker.presentation.view.states.AddUserState
import rs.raf.nutritiontracker.presentation.view.states.UserState

interface UserContract {
    interface ViewModel {
        val userState: LiveData<UserState>
        val addUserState: LiveData<AddUserState>

        fun getUserByUsername(username: String)
        fun insertUser(user: UserEntity)
    }
}