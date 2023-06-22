package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.repositories.MealRepository
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.view.states.AddMealState
import rs.raf.nutritiontracker.presentation.view.states.MealsState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MealViewModel(
    private val mealRepository: MealRepository
) : ViewModel(), MealContract.ViewModel {
    private val subscriptions = CompositeDisposable()
    override val mealsState: MutableLiveData<MealsState> = MutableLiveData()
    override val addMealDone: MutableLiveData<AddMealState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()


    init {
        // TODO: Namestiti da odmah povlaci iz kategorija sva jela
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                mealRepository
                    .getAllByName(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    mealsState.value = MealsState.Success(it)
                },
                {
                    mealsState.value = MealsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllMeals() {
        TODO("Not yet implemented")
    }

    override fun getAllMeals() {
        TODO("Not yet implemented")
    }

    override fun getMealsByName(name: String) {
        TODO("Not yet implemented")
    }

    override fun addMeal(meal: Meal) {
        TODO("Not yet implemented")
    }

}