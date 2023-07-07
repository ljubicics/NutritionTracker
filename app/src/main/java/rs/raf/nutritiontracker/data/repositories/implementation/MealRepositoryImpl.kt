package rs.raf.nutritiontracker.data.repositories.implementation

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasources.local.CategoryDao
import rs.raf.nutritiontracker.data.datasources.local.MealDao
import rs.raf.nutritiontracker.data.datasources.local.MealSavedDao
import rs.raf.nutritiontracker.data.datasources.remote.MealService
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.entities.MealEntity
import rs.raf.nutritiontracker.data.models.MealForCategory
import rs.raf.nutritiontracker.data.models.response.MealResponse
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.models.entities.MealSavedEntity
import rs.raf.nutritiontracker.data.repositories.specification.MealRepository
import rs.raf.nutritiontracker.data.repositories.specification.MealsForCategoryRepository

class MealRepositoryImpl(
    private val localMealDataSource: MealDao,
    private val localMealSavedDataSource: MealSavedDao,
    private val localCategoryDataSource: CategoryDao,
    private val remoteDataSource: MealService,
    private val mealsForCategoryRepository: MealsForCategoryRepository
    ) : MealRepository {

    @SuppressLint("CheckResult")
    override fun fetchAll(): Observable<Resource<Unit>> {
        val list: Observable<List<MealForCategory>> = mealsForCategoryRepository.getAll()

        val mealIdList: Observable<List<String>> = list.map {
            it.map {
                it.idMeal
            }
        }

        var mealResponseList: MutableList<MealResponse> = mutableListOf()
        mealIdList.subscribe(
            {
                for(id in it) {
                    remoteDataSource
                        .getMealForId(id)
                        .subscribe(
                            {
                                mealResponseList.add(it)
                            },
                            {
                                println("ERRORRRRRR1 $it")
                            }
                        )
                }
                println("VELICINA MEAL RESPONSE LIST " + mealResponseList)
            },
            {
                println("ERRORRRRRR2")
            }
        )

        println(mealIdList)
        return Observable.just(Resource.Success(Unit))
    }

    @SuppressLint("CheckResult")
    override fun fetchMealsForCategory(category: String): Observable<Resource<Unit>> {
        val list: Observable<List<MealForCategory>> = mealsForCategoryRepository.getAllByCategory(category)
        val mealIdList: Observable<List<String>> = list.map {
            it.map {
                it.idMeal
            }
        }

        var mealResponseList: MutableList<MealResponse> = mutableListOf()
        mealIdList.subscribe(
            {
                for(id in it) {
                    remoteDataSource
                        .getMealForId(id)
                        .subscribe(
                            {
                                mealResponseList.add(it)
                            },
                            {
                                println("ERRORRRRRR1 $it")
                            }
                        )
                }
                val entities = mealResponseList.map {
                    it.meals.map {
                        MealEntity(
                            mealId = 0,
                            it.idMeal,
                            it.strMeal,
                            it.strDrinkAlternate,
                            it.strCategory,
                            it.strArea,
                            it.strInstructions,
                            it.strMealThumb,
                            it.strTags,
                            it.strYoutube,
                            it.strIngredient1,
                            it.strIngredient2,
                            it.strIngredient3,
                            it.strIngredient4,
                            it.strIngredient5,
                            it.strIngredient6,
                            it.strIngredient7,
                            it.strIngredient8,
                            it.strIngredient9,
                            it.strIngredient10,
                            it.strIngredient11,
                            it.strIngredient12,
                            it.strIngredient13,
                            it.strIngredient14,
                            it.strIngredient15,
                            it.strIngredient16,
                            it.strIngredient17,
                            it.strIngredient18,
                            it.strIngredient19,
                            it.strIngredient20,
                            it.strMeasure1,
                            it.strMeasure2,
                            it.strMeasure3,
                            it.strMeasure4,
                            it.strMeasure5,
                            it.strMeasure6,
                            it.strMeasure7,
                            it.strMeasure8,
                            it.strMeasure9,
                            it.strMeasure10,
                            it.strMeasure11,
                            it.strMeasure12,
                            it.strMeasure13,
                            it.strMeasure14,
                            it.strMeasure15,
                            it.strMeasure16,
                            it.strMeasure17,
                            it.strMeasure18,
                            it.strMeasure19,
                            it.strMeasure20,
                            it.strSource,
                            it.strImageSource,
                            it.strCreativeCommonsConfirmed,
                            it.dateModified
                        )
                    }
                }
                val entitiesForDatabase: MutableList<MealEntity> = mutableListOf()
                for(item in entities) {
                    entitiesForDatabase.addAll(item)
                }
                localMealDataSource.deleteAndInsertAll(entitiesForDatabase)
            },
            {
                println("ERRORRRRRR2")
            }
        )
        val listica: Observable<List<MealResponse>> = Observable.just(mealResponseList)
        return listica.map {
            Resource.Success(Unit)
        }
//        return Observable.just(Resource.Success(Unit))
    }

    override fun fetchMealById(id: String): Observable<Resource<List<Meal>>> {
        val meals =  remoteDataSource
            .fetchAllMealsById(id)
            .map {
                it.meals.map {
                        Meal(
                            it.idMeal,
                            it.strMeal,
                            it.strDrinkAlternate,
                            it.strCategory,
                            it.strArea,
                            it.strInstructions,
                            it.strMealThumb,
                            it.strTags,
                            it.strYoutube,
                            it.strIngredient1,
                            it.strIngredient2,
                            it.strIngredient3,
                            it.strIngredient4,
                            it.strIngredient5,
                            it.strIngredient6,
                            it.strIngredient7,
                            it.strIngredient8,
                            it.strIngredient9,
                            it.strIngredient10,
                            it.strIngredient11,
                            it.strIngredient12,
                            it.strIngredient13,
                            it.strIngredient14,
                            it.strIngredient15,
                            it.strIngredient16,
                            it.strIngredient17,
                            it.strIngredient18,
                            it.strIngredient19,
                            it.strIngredient20,
                            it.strMeasure1,
                            it.strMeasure2,
                            it.strMeasure3,
                            it.strMeasure4,
                            it.strMeasure5,
                            it.strMeasure6,
                            it.strMeasure7,
                            it.strMeasure8,
                            it.strMeasure9,
                            it.strMeasure10,
                            it.strMeasure11,
                            it.strMeasure12,
                            it.strMeasure13,
                            it.strMeasure14,
                            it.strMeasure15,
                            it.strMeasure16,
                            it.strMeasure17,
                            it.strMeasure18,
                            it.strMeasure19,
                            it.strMeasure20,
                            it.strSource,
                            it.strImageSource,
                            it.strCreativeCommonsConfirmed,
                            it.dateModified
                        )
                }
            }
        return meals.map {
            Resource.Success(it)
        }
    }

    override fun fetchMealByName(name: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .fetchByMealName(name)
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<Meal>> {
        TODO("Not yet implemented")
    }

    override fun getAllByUser(name: String): Observable<List<MealSavedEntity>> {
        return localMealSavedDataSource.getByUser(name)
    }

    override fun insert(meal: MealSavedEntity): Completable {
        return localMealSavedDataSource.insert(meal)
    }
}