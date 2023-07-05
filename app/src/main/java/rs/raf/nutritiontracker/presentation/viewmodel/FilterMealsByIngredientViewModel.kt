package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.repositories.specification.MealsForIngredientRepository
import rs.raf.nutritiontracker.presentation.contract.MealsForIngredientContract
import rs.raf.nutritiontracker.presentation.view.states.MealsForAreaState
import rs.raf.nutritiontracker.presentation.view.states.MealsForIngredientState
import timber.log.Timber

class FilterMealsByIngredientViewModel (
    private val mealsForIngredientRepository: MealsForIngredientRepository,
) : ViewModel(), MealsForIngredientContract.ViewModel {
    private val subscriptions = CompositeDisposable()
    override val mealsForIngredientState: MutableLiveData<MealsForIngredientState> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    override fun fetchAllMealsForIngredient(ingredient: String) {
        val subscription = mealsForIngredientRepository
            .fetchAllByIngredient(ingredient)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsForIngredientState.value = MealsForIngredientState.Loading
                        is Resource.Success -> mealsForIngredientState.value = MealsForIngredientState.Success(it.data)
                        is Resource.Error -> mealsForIngredientState.value = MealsForIngredientState.Error("There are no meals for seleced ingredient")
                    }
                },
                {
                    mealsForIngredientState.value = MealsForIngredientState.Error("There are no meals for seleced ingredient")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
}