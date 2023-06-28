package rs.raf.nutritiontracker.data.repositories.specification

import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.Area
import rs.raf.nutritiontracker.data.models.Resource

interface AreaRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Area>>
}