package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.repositories.specification.CategoryRepository
import rs.raf.nutritiontracker.presentation.contract.CategoryContract
import rs.raf.nutritiontracker.presentation.view.states.AddCategoryState
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel(), CategoryContract.ViewModel{
    private val subscriptions = CompositeDisposable()
    override val categoriesState: MutableLiveData<CategoriesState> = MutableLiveData()
    override val addCategoryDone: MutableLiveData<AddCategoryState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()


    // Sluzi za dohvatanje podataka prilikom filtriranja
    init {
        // TODO: Namestiti da odmah povlaci iz kategorija sva jela
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)// pauza pre nego sto se izvrsi ono sto je planirano
            .distinctUntilChanged()// Ovo se okine samo ukoliko je doslo do neke promene
            .switchMap {
                // Kazemo sta zelimo da se desava prilikom switchovanja subscriptiona
                categoryRepository
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
                    categoriesState.value = CategoriesState.Success(it)
                },
                {
                    categoriesState.value = CategoriesState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }


    override fun fetchAllCategories() {
        val subscription = categoryRepository
            .fetchAll()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> categoriesState.value = CategoriesState.Loading
                        is Resource.Success -> categoriesState.value = CategoriesState.DataFetched
                        is Resource.Error -> categoriesState.value = CategoriesState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    categoriesState.value = CategoriesState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllCategories() {
        TODO("Not yet implemented")
    }

    override fun getCategoriesByName(name: String) {
        TODO("Not yet implemented")
    }

    override fun addCateogry(meal: Meal) {
        TODO("Not yet implemented")
    }
}