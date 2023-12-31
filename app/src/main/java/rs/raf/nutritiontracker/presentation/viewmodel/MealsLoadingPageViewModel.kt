package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.repositories.specification.MealsForCategoryRepository
import rs.raf.nutritiontracker.presentation.contract.MealsForCategoryContract
import rs.raf.nutritiontracker.presentation.view.states.AddMealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import timber.log.Timber
import java.util.concurrent.TimeUnit


/*
ViewModel simmilar to MealsForCategoryViewModel, used to save all meals
When filtering on loading page
 */
class MealsLoadingPageViewModel(
    val mealsForCategoryRepository: MealsForCategoryRepository
) : ViewModel(), MealsForCategoryContract.ViewModel {
    private val subscriptions = CompositeDisposable()
    override val mealsForCategoryState: MutableLiveData<MealsForCategoryState> = MutableLiveData()
    override val addMealsForCategoryDone: MutableLiveData<AddMealsForCategoryState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        // TODO: Ponoviti sta je ovo
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)// pauza pre nego sto se izvrsi ono sto je planirano
            .distinctUntilChanged()// Ovo se okine samo ukoliko je doslo do neke promene
            .switchMap {
                // Kazemo sta zelimo da se desava prilikom switchovanja subscriptiona
                mealsForCategoryRepository
                    .getAllMealsByName(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    mealsForCategoryState.value = MealsForCategoryState.Success(it)
                },
                {
                    mealsForCategoryState.value = MealsForCategoryState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllMealsForCategory() {
        val subscription = mealsForCategoryRepository
            .fetchAll()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsForCategoryState.value = MealsForCategoryState.Loading
                        is Resource.Success -> mealsForCategoryState.value = MealsForCategoryState.DataFetched
                        is Resource.Error -> mealsForCategoryState.value = MealsForCategoryState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealsForCategoryState.value = MealsForCategoryState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllMealsForArea(area: String) {
        TODO("Not yet implemented")
    }

    override fun getAllMeals() {
        val subscription = mealsForCategoryRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsForCategoryState.value = MealsForCategoryState.Success(it)
                },
                {
                    mealsForCategoryState.value = MealsForCategoryState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMealsForCategory(categoryName: String) {
        val subscription = mealsForCategoryRepository
            .getAllByCategory(categoryName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsForCategoryState.value = MealsForCategoryState.Success(it)
                },
                {
                    mealsForCategoryState.value = MealsForCategoryState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMealsByName(mealName: String) {
        val subscription = mealsForCategoryRepository
            .getAllMealsByName(mealName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsForCategoryState.value = MealsForCategoryState.Success(it)
                },
                {
                    mealsForCategoryState.value = MealsForCategoryState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}