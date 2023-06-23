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
import rs.raf.nutritiontracker.data.repositories.CategoryRepository
import rs.raf.nutritiontracker.data.repositories.MealRepository
import rs.raf.nutritiontracker.presentation.contract.MealContract
import rs.raf.nutritiontracker.presentation.view.states.AddMealState
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import rs.raf.nutritiontracker.presentation.view.states.MealsState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MealViewModel(
    private val mealRepository: MealRepository,
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

    @SuppressLint("CheckResult")
    override fun fetchAllMeals() {
//        var categoryStrs = mutableListOf<String>() // lista naziva kategorija
        // TODO: Ovde zelim da pozovem samo metodu iz repository-ja to znaci da treba da pozovem metodu iz meals for category
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
                        is Resource.Loading -> mealsState.value = MealsState.Loading
                        is Resource.Success -> mealsState.value = MealsState.DataFetched
                        is Resource.Error -> mealsState.value = MealsState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealsState.value = MealsState.Error("Error happened while fetching data from the server")
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

    override fun addMeal(meal: Meal) {
        TODO("Not yet implemented")
    }

}