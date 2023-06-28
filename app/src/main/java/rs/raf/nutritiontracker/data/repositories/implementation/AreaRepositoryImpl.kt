package rs.raf.nutritiontracker.data.repositories.implementation

import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasources.local.AreaDao
import rs.raf.nutritiontracker.data.datasources.remote.CategoryService
import rs.raf.nutritiontracker.data.models.Area
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.models.entities.AreaEntity
import rs.raf.nutritiontracker.data.repositories.specification.AreaRepository

class AreaRepositoryImpl(
    private val remoteDataSource: CategoryService,
    private val localDataSource: AreaDao
) : AreaRepository {
    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
            .fetchAllAreas()
            .doOnNext {
                val entities = it.meals.map {
                    AreaEntity(
                        it.strArea
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<Area>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                   Area(
                       it.strArea
                   )
                }
            }
    }


}