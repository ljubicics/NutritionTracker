package rs.raf.nutritiontracker.data.repositories.specification

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.data.models.Resource

interface CategoryRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Category>>
    fun getAllByName(name: String): Observable<List<Category>>
    fun insert(category: Category): Completable
}