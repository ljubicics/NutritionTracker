package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.repositories.specification.AreaRepository
import rs.raf.nutritiontracker.data.repositories.specification.MealsForAreaRepository
import rs.raf.nutritiontracker.data.repositories.specification.MealsForCategoryRepository
import rs.raf.nutritiontracker.presentation.contract.MealsForAreaContract
import rs.raf.nutritiontracker.presentation.view.states.AreaState
import rs.raf.nutritiontracker.presentation.view.states.MealsForAreaState
import timber.log.Timber

class FilterMealsByAreaViewModel (
    private val mealsForAreaRepository: MealsForAreaRepository,
    private val areaRepository: AreaRepository
) : ViewModel(), MealsForAreaContract.ViewModel {
    private val subscriptions = CompositeDisposable()
    override val mealsForAreaState: MutableLiveData<MealsForAreaState> = MutableLiveData()
    override val areaState: MutableLiveData<AreaState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

//    init {
//        // TODO: Namestiti da odmah povlaci iz kategorija sva jela
//        val subscription = publishSubject
//            .debounce(200, TimeUnit.MILLISECONDS)// pauza pre nego sto se izvrsi ono sto je planirano
//            .distinctUntilChanged()// Ovo se okine samo ukoliko je doslo do neke promene
//            .switchMap {
//                // Kazemo sta zelimo da se desava prilikom switchovanja subscriptiona
//                mealsForCategoryRepository
//                    .getAllMealsByName(it)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnError {
//                        Timber.e("Error in publish subject")
//                        Timber.e(it)
//                    }
//            }
//            .subscribe(
//                {
//                    mealsForAreaState.value = MealsForAreaState.Success(it)
//                },
//                {
//                    mealsForAreaState.value = MealsForAreaState.Error("Error happened while fetching data from db")
//                    Timber.e(it)
//                }
//            )
//        subscriptions.add(subscription)
//    }

    override fun fetchAllMealsForArea(area: String) {
        val subscription = mealsForAreaRepository
            .fetchAllByArea(area)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsForAreaState.value = MealsForAreaState.Loading
                        is Resource.Success -> mealsForAreaState.value = MealsForAreaState.Success(it.data)
                        is Resource.Error -> mealsForAreaState.value = MealsForAreaState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealsForAreaState.value = MealsForAreaState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllAreas() {
        val subscription = areaRepository
            .fetchAll()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> areaState.value = AreaState.Loading
                        is Resource.Success -> areaState.value = AreaState.DataFetched
                        is Resource.Error -> areaState.value = AreaState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    areaState.value = AreaState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllAreas() {
        val subscription = areaRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    areaState.value = AreaState.Success(it)
                },
                {
                    areaState.value = AreaState.Error("Error happened while fetching data from db")
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