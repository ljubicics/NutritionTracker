package rs.raf.nutritiontracker.data.repositories

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rs.raf.nutritiontracker.data.datasources.local.CategoryDao
import rs.raf.nutritiontracker.data.datasources.local.MealsForCategoryDao
import rs.raf.nutritiontracker.data.datasources.remote.MealService
import rs.raf.nutritiontracker.data.models.AllMealsForCategoryResponse
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.data.models.CategoryEntity
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.MealForCategoryEntity
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.models.StrPlusAllMeals
import timber.log.Timber

class MealsForCategoryRepositoryImpl(
    private val localMealsForCategoryDataSource: MealsForCategoryDao,
    private val remoteDataSource: MealService,
    private val localCategoryDataSource: CategoryDao,
    private val categoryRepository: CategoryRepository,
) : MealsForCategoryRepository{

    @SuppressLint("CheckResult")
    override fun fetchAll(): Observable<Resource<Unit>> {
        val list : Observable<List<Category>> = categoryRepository.getAll()

        // Uzimam imena svih kategorija
        val strCategories : Observable<List<String>> = list.map { it ->
            it.map {
                it.strCategory
            } 
        }

        // Pravim listu AllMealsForCategory
        var listOfAllMealsForCategory : MutableList<AllMealsForCategoryResponse> = mutableListOf()
        var listOfStrPlusAllMeals : MutableList<StrPlusAllMeals> = mutableListOf()
        strCategories.subscribe(
            {
                for(str in it) {
                    remoteDataSource
                        .getAllMealsForCategory(str)
                        .subscribe(
                            {
                                listOfAllMealsForCategory.add(it)
                                val tmp = StrPlusAllMeals(
                                    it,
                                    str
                                )
                                listOfStrPlusAllMeals.add(tmp)
                                println("VELICINA PLUS ALL MEALS " + listOfStrPlusAllMeals.size)
                            },
                            {
                                println("ERRORRRRRRR")
                            }
                        )
                }
                // prolazim kroz listu i pravim List<List<MealForCategoryEntity>>
                // {
                //     {jelo1, jelo2},
                //     {jelo1, jelo2}
                // }
//                val entities = listOfAllMealsForCategory.map {
//                    it.meals.map {
//                       MealForCategoryEntity(
//                           it.strMeal,
//                           it.strMealThumb,
//                           it.idMeal,
//
//                       )
//                    }
//                }

                var entitiesForDatabase: MutableList<MealForCategoryEntity> = mutableListOf()
                for(item in listOfStrPlusAllMeals) {
                    for(i in item.allMealsForCategoryResponse.meals) {
                        val entity = MealForCategoryEntity (
                                        i.strMeal,
                                        i.strMealThumb,
                                        i.idMeal,
                                        item.strCategory
                                    )
                        entitiesForDatabase.add(entity)
                    }
                }

                // Prolazim kroz List<List<MealForCategoryEntity>> i dodajem unutrasnju listu u mutableList
//                val entitiesForDatabase: MutableList<MealForCategoryEntity> = mutableListOf()
//                for(item in entities) {
//                    entitiesForDatabase.addAll(item)
//                }
                println("VELICINAAA" + entitiesForDatabase.size)
                localMealsForCategoryDataSource.deleteAndInsertAll(entitiesForDatabase)
            },
            {
                println("ERRORRRRRRR")
            }
        )
        return Observable.just(Resource.Success(Unit))
    }

    override fun getAll(): Observable<List<MealForCategory>> {
        return localMealsForCategoryDataSource
            .getAll()
            .map{
                it.map {
                    MealForCategory(
                        it.strMeal,
                        it.strMealThumb,
                        it.idMeal,
                        it.strCategory
                    )
                }
            }
    }

    override fun getAllByCategory(category: String): Observable<List<MealForCategory>> {
        return localMealsForCategoryDataSource
            .getAllForCategory(category)
            .map {
                it.map {
                    MealForCategory(
                        it.strMeal,
                        it.strMealThumb,
                        it.idMeal,
                        it.strCategory
                    )
                }
            }
    }
}