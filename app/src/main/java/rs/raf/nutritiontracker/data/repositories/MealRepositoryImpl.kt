package rs.raf.nutritiontracker.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasources.local.MealDao
import rs.raf.nutritiontracker.data.datasources.remote.MealService
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.data.models.CategoryEntity
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.Resource
import timber.log.Timber

class MealRepositoryImpl(
    private val localDataSource: MealDao,
    private val remoteDataSource: MealService
    ) : MealRepository {
    override fun fetchAll(categories: List<String>): Observable<Resource<Unit>> {
        TODO("Not yet implemented")

//        for(category in categories) {
//            remoteDataSource
//                .getAllMealsForCategory(category)
//                .doOnNext({
//                    Timber.e("Upis u bazu")
//                            println("AAAAAAAAA")
//                    val entities = it.categories.map {
//                CategoryEntity(
//                    it.idCategory,
//                    it.strCategory,
//                    it.strCategoryThumb,
//                    it.strCategoryDescription
//                )
//            }
//            localDataSource.deleteAndInsertAll(entities)
//        }
//            .map {
//                Resource.Success(Unit)
//            }
//                )
    }

    override fun getAll(): Observable<List<Meal>> {
        TODO("Not yet implemented")
    }

    override fun getAllByName(name: String): Observable<List<Meal>> {
        TODO("Not yet implemented")
    }

    override fun insert(meal: Meal): Completable {
        TODO("Not yet implemented")
    }
}