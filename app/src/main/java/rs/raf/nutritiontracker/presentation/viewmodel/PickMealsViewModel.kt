package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.nutritiontracker.data.models.DayInTheWeek
import rs.raf.nutritiontracker.data.models.SavedMeal
import rs.raf.nutritiontracker.data.repositories.specification.MealRepository
import rs.raf.nutritiontracker.data.repositories.specification.MealsForCategoryRepository
import rs.raf.nutritiontracker.presentation.contract.PickMealsContract
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.SavedMealState
import timber.log.Timber

class PickMealsViewModel(
    private val mealRepository: MealRepository,
    private val mealsForCategoryRepository: MealsForCategoryRepository,
) : ViewModel(), PickMealsContract.ViewModel {
    override val remoteMealsState: MutableLiveData<MealsForCategoryState> = MutableLiveData()
    override val localMealsState: MutableLiveData<SavedMealState> = MutableLiveData()
    override val mondayState: MutableLiveData<List<SavedMeal>> = MutableLiveData()
    override val tuesdayState: MutableLiveData<List<SavedMeal>> = MutableLiveData()
    override val wednesdayState: MutableLiveData<List<SavedMeal>> = MutableLiveData()
    override val thursdayState: MutableLiveData<List<SavedMeal>> = MutableLiveData()
    override val fridayState: MutableLiveData<List<SavedMeal>> = MutableLiveData()
    override val saturdayState: MutableLiveData<List<SavedMeal>> = MutableLiveData()
    override val sundayState: MutableLiveData<List<SavedMeal>> = MutableLiveData()

    private val subscriptions = CompositeDisposable()


    override fun addMealToDay(meal: SavedMeal, day: DayInTheWeek) {
        when(day) {
            DayInTheWeek.MONDAY -> {
                val currentList = mondayState.value
                if(currentList == null) {
                    val newList = listOf(meal)
                    mondayState.value = newList
                } else {
                    val newList = currentList!!.toMutableList()
                    newList.add(meal)
                    mondayState.value = newList
                }
            }
            DayInTheWeek.TUESDAY -> {
                val currentList = tuesdayState.value
                if(currentList == null) {
                    val newList = listOf(meal)
                    tuesdayState.value = newList
                } else {
                    val newList = currentList!!.toMutableList()
                    newList.add(meal)
                    tuesdayState.value = newList
                }
            }
            DayInTheWeek.WEDNESDAY -> {
                val currentList = wednesdayState.value
                if(currentList == null) {
                    val newList = listOf(meal)
                    wednesdayState.value = newList
                } else {
                    val newList = currentList!!.toMutableList()
                    newList.add(meal)
                    wednesdayState.value = newList
                }
            }
            DayInTheWeek.THURSDAY -> {
                val currentList = thursdayState.value
                if(currentList == null) {
                    val newList = listOf(meal)
                    thursdayState.value = newList
                } else {
                    val newList = currentList!!.toMutableList()
                    newList.add(meal)
                    thursdayState.value = newList
                }
            }
            DayInTheWeek.FRIDAY -> {
                val currentList = fridayState.value
                if(currentList == null) {
                    val newList = listOf(meal)
                    fridayState.value = newList
                } else {
                    val newList = currentList!!.toMutableList()
                    newList.add(meal)
                    fridayState.value = newList
                }
            }
            DayInTheWeek.SATURDAY -> {
                val currentList = saturdayState.value
                if(currentList == null) {
                    val newList = listOf(meal)
                    saturdayState.value = newList
                } else {
                    val newList = currentList!!.toMutableList()
                    newList.add(meal)
                    saturdayState.value = newList
                }
            }
            DayInTheWeek.SUNDAY -> {
                val currentList = sundayState.value
                if(currentList == null) {
                    val newList = listOf(meal)
                    sundayState.value = newList
                } else {
                    val newList = currentList!!.toMutableList()
                    newList.add(meal)
                    sundayState.value = newList
                }
            }
        }
    }

    override fun removeMealFromDay(id: Long, day: DayInTheWeek) {
        when(day) {
            DayInTheWeek.MONDAY -> {
                val currentList = mondayState.value
                val newList = currentList!!.toMutableList()
                newList.removeIf { it.mealId == id }
                mondayState.value = newList
            }
            DayInTheWeek.TUESDAY -> {
                val currentList = tuesdayState.value
                val newList = currentList!!.toMutableList()
                newList.removeIf { it.mealId == id }
                tuesdayState.value = newList
            }
            DayInTheWeek.WEDNESDAY -> {
                val currentList = fridayState.value
                val newList = currentList!!.toMutableList()
                newList.removeIf { it.mealId == id }
                fridayState.value = newList
            }
            DayInTheWeek.THURSDAY -> {
                val currentList = fridayState.value
                val newList = currentList!!.toMutableList()
                newList.removeIf { it.mealId == id }
                fridayState.value = newList
            }
            DayInTheWeek.FRIDAY -> {
                val currentList = fridayState.value
                val newList = currentList!!.toMutableList()
                newList.removeIf { it.mealId == id }
                fridayState.value = newList
            }
            DayInTheWeek.SATURDAY -> {
                val currentList = fridayState.value
                val newList = currentList!!.toMutableList()
                newList.removeIf { it.mealId == id }
                fridayState.value = newList
            }
            DayInTheWeek.SUNDAY -> {
            val currentList = fridayState.value
            val newList = currentList!!.toMutableList()
            newList.removeIf { it.mealId == id }
            fridayState.value = newList
            }
        }
    }

    override fun getAllMealsRemote() {
        val subscription = mealsForCategoryRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    remoteMealsState.value = MealsForCategoryState.Success(it)
                },
                {
                    remoteMealsState.value = MealsForCategoryState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMealsLocal(username: String) {
        val subscription = mealRepository
            .getAllByUser(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Meal inserted")
                    localMealsState.value = SavedMealState.Success(it)
                },
                {
                    Timber.e("error inserting meal")
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}