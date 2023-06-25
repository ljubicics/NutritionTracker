package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.nutritiontracker.data.models.entities.UserEntity
import rs.raf.nutritiontracker.data.repositories.specification.UserRepository
import rs.raf.nutritiontracker.presentation.contract.UserContract
import rs.raf.nutritiontracker.presentation.view.states.AddUserState
import rs.raf.nutritiontracker.presentation.view.states.CategoriesState
import rs.raf.nutritiontracker.presentation.view.states.UserState
import timber.log.Timber

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel(), UserContract.ViewModel{
    private val subscriptions = CompositeDisposable()
    override val userState: MutableLiveData<UserState> = MutableLiveData()
    override val addUserState: MutableLiveData<AddUserState> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    override fun getUserByUsername(username: String) {
        val subscription = userRepository
            .getUserByUsername(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    userState.value = UserState.Success(it)
                },
                {
                    userState.value = UserState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insertUser(user: UserEntity) {
        val subscription = userRepository
            .insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addUserState.value = AddUserState.Success
                },
                {
                    addUserState.value = AddUserState.Error("Error happened while adding movie")
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