package rs.raf.nutritiontracker.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.models.entities.MealEntity
import rs.raf.nutritiontracker.data.models.entities.MealSavedEntity
import rs.raf.nutritiontracker.data.repositories.specification.MealRepository
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.view.states.AddMealState
import rs.raf.nutritiontracker.presentation.view.states.MealState
import rs.raf.nutritiontracker.presentation.view.states.SavedMealState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MealViewModel(
    private val mealRepository: MealRepository,
) : ViewModel(), MealContract.ViewModel {
    private val subscriptions = CompositeDisposable()
    override val mealsState: MutableLiveData<MealState> = MutableLiveData()
    override val savedMealState: MutableLiveData<SavedMealState> = MutableLiveData()
    override val addMealDone: MutableLiveData<AddMealState> = MutableLiveData()

    @SuppressLint("CheckResult")
    override fun fetchAllMeals() {
//        var categoryStrs = mutableListOf<String>() // lista naziva kategorija
        // Pa onda da prolazim kroz to i da preko id-ja dobijem celo jelo
        // Ali se to mora resavati u meal repository-ju
        val subscription = mealRepository
            .fetchAll()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsState.value = MealState.Loading
                        is Resource.Success -> mealsState.value = MealState.DataFetched
                        is Resource.Error -> mealsState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealsState.value = MealState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllMealsForCategory(category: String) {
        val subscription = mealRepository
            .fetchMealsForCategory(category)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsState.value = MealState.Loading
                        is Resource.Success -> mealsState.value = MealState.DataFetched
                        is Resource.Error -> mealsState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealsState.value = MealState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllMealsByName(name: String) {
        val subscription = mealRepository
            .fetchMealByName(name)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsState.value = MealState.Loading
                        is Resource.Success -> mealsState.value = MealState.DataFetched
                        is Resource.Error -> mealsState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealsState.value = MealState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchMealById(id: String) {
        val subscription = mealRepository
            .fetchMealById(id)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsState.value = MealState.Loading
                        is Resource.Success -> mealsState.value = MealState.Success(it.data)
                        is Resource.Error -> mealsState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealsState.value = MealState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMeals() {
        TODO("Not yet implemented")
    }

    override fun getMealsByName(name: String) {
        TODO("Not yet implemented")
    }

    override fun deleteMealById(id: Long) {
        val subscription = mealRepository
            .deleteMealById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
//                    deleteMealFromList(id)
                    Timber.e("Meal deleted")
                },
                {
                    Timber.e("error deleting meal")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getSavedMealsByUser(username: String) {
        val subscription = mealRepository
            .getAllByUser(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Meal inserted")
                    savedMealState.value = SavedMealState.Success(it)
                },
                {
                    Timber.e("error inserting meal")
                }
            )
        subscriptions.add(subscription)
    }

    override fun addMeal(meal: MealSavedEntity) {
        val subscription = mealRepository
            .insert(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Meal inserted")
                    addMealDone.value = AddMealState.Success
                },
                {
                    Timber.e("error inserting meal")
                }
            )
        subscriptions.add(subscription)
    }

    override fun editMeal(meal: MealSavedEntity) {
        val subscription = mealRepository
            .editMeal(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Meal inserted")
                    addMealDone.value = AddMealState.Success
                },
                {
                    Timber.e("error inserting meal")
                }
            )
        subscriptions.add(subscription)
    }

    private fun deleteMealFromList(id: Long) {
        if(savedMealState.value is SavedMealState.Success) {
            val list = (savedMealState.value as SavedMealState.Success).savedMeals.toMutableList()
            list.removeIf { it.mealId == id }
            savedMealState.value = SavedMealState.Success(list)
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}