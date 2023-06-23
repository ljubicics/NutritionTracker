package rs.raf.nutritiontracker.data.repositories

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rs.raf.nutritiontracker.data.datasources.local.CategoryDao
import rs.raf.nutritiontracker.data.datasources.local.MealDao
import rs.raf.nutritiontracker.data.datasources.remote.MealService
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.data.models.CategoryEntity
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.MealEntity
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.OneMealForCategoryResponse
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.presentation.view.states.MealsState
import timber.log.Timber
import java.util.concurrent.CountDownLatch

class MealRepositoryImpl(
    private val localMealDataSource: MealDao,
    private val localCategoryDataSource: CategoryDao,
    private val remoteDataSource: MealService,
    private val mealsForCategoryRepository: MealsForCategoryRepository
    ) : MealRepository {

    override fun fetchAll(): Observable<Resource<Unit>> {
        TODO("Not yet implemented")
        val list: Observable<List<MealForCategory>> = mealsForCategoryRepository.getAll()

        val mealIdList: Observable<Meal>
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