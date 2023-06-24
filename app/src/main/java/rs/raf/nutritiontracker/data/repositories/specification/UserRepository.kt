package rs.raf.nutritiontracker.data.repositories.specification

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.Meal

interface UserRepository {
    fun getUserByUsername(name: String): Observable<List<Meal>>
    fun insertUser(meal: Meal): Completable
}