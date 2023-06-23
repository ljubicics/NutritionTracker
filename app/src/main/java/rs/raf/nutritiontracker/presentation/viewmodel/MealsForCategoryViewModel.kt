package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.repositories.MealsForCategoryRepository
import rs.raf.nutritiontracker.presentation.contract.MealsForCategoryContract
import rs.raf.nutritiontracker.presentation.view.states.AddMealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import timber.log.Timber

class MealsForCategoryViewModel(
    private val mealsForCategoryRepository: MealsForCategoryRepository
) : ViewModel(), MealsForCategoryContract.ViewModel {
    private val subscriptions = CompositeDisposable()
    override val mealsForCategoryState: MutableLiveData<MealsForCategoryState> = MutableLiveData()
    override val addMealsForCategoryDone: MutableLiveData<AddMealsForCategoryState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

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

    override fun getAllMealsForCategory() {
        TODO("Not yet implemented")
    }

}