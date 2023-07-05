package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.repositories.specification.MealsForCategoryRepository
import rs.raf.nutritiontracker.data.repositories.specification.MealsForIngredientRepository
import rs.raf.nutritiontracker.presentation.contract.MealsForCategoryContract
import rs.raf.nutritiontracker.presentation.contract.MealsForIngredientContract
import rs.raf.nutritiontracker.presentation.view.states.AddMealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealsForCategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealsForIngredientState
import timber.log.Timber

class ListMealsViewModel(
    private val mealsForCategoryRepository: MealsForCategoryRepository,
    private val mealsForIngredientRepository: MealsForIngredientRepository
) : ViewModel(), MealsForCategoryContract.ViewModel, MealsForIngredientContract.ViewModel{
    private val subscriptions = CompositeDisposable()
    override val mealsForCategoryState: MutableLiveData<MealsForCategoryState> = MutableLiveData()
    override val addMealsForCategoryDone: MutableLiveData<AddMealsForCategoryState> = MutableLiveData()
    override val mealsForIngredientState: MutableLiveData<MealsForIngredientState> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

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

    override fun fetchAllMealsForCategory() {
        TODO("Not yet implemented")
    }

    override fun fetchAllMealsForArea(area: String) {
        TODO("Not yet implemented")
    }

    override fun getAllMealsForCategory(categoryName: String) {
        TODO("Not yet implemented")
    }

    override fun getAllMealsByName(mealName: String) {
        TODO("Not yet implemented")
    }

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

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}