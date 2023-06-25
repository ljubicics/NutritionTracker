package rs.raf.nutritiontracker.data.repositories.specification

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.User
import rs.raf.nutritiontracker.data.models.entities.UserEntity

interface UserRepository {
    fun getUserByUsername(name: String): Observable<List<User>>
    fun insertUser(user: UserEntity): Completable
}