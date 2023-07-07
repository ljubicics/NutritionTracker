package rs.raf.nutritiontracker.data.repositories.specification

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.models.entities.MealEntity
import rs.raf.nutritiontracker.data.models.entities.MealSavedEntity

interface MealRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun fetchMealsForCategory(category: String): Observable<Resource<Unit>>
    fun fetchMealByName(name: String): Observable<Resource<Unit>>
    fun fetchMealById(id: String): Observable<Resource<List<Meal>>>
    fun getAll(): Observable<List<Meal>>
    fun getAllByName(name: String): Observable<List<Meal>>
    fun insert(meal: MealSavedEntity): Completable
}