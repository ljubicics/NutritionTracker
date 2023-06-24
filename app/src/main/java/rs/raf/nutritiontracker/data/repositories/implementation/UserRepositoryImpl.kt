package rs.raf.nutritiontracker.data.repositories.implementation

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasources.local.UserDao
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.repositories.specification.UserRepository

class UserRepositoryImpl(
    private val localDataSource: UserDao
) : UserRepository {
    override fun getUserByUsername(name: String): Observable<List<Meal>> {
        TODO("Not yet implemented")
    }

    override fun insertUser(meal: Meal): Completable {
        TODO("Not yet implemented")
    }

}