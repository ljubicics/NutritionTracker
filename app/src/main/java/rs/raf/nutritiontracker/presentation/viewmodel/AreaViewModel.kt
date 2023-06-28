package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.repositories.specification.AreaRepository
import rs.raf.nutritiontracker.presentation.contract.AreaContract
import rs.raf.nutritiontracker.presentation.view.states.AddCategoryState
import rs.raf.nutritiontracker.presentation.view.states.AreaState
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AreaViewModel(
    private val areaRepository: AreaRepository
) : ViewModel(), AreaContract.ViewModel {
    private val subscriptions = CompositeDisposable()
    override val areaState: MutableLiveData<AreaState> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

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