package rs.raf.nutritiontracker.data.repositories.implementation

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasources.local.UserDao
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.data.models.Meal
import rs.raf.nutritiontracker.data.models.User
import rs.raf.nutritiontracker.data.models.entities.UserEntity
import rs.raf.nutritiontracker.data.repositories.specification.UserRepository

class UserRepositoryImpl(
    private val localDataSource: UserDao
) : UserRepository {
    override fun getUserByUsername(name: String): Observable<List<User>> {
        return localDataSource
            .getUserByUsername(name)
            .map {
                it.map {
                    User(
                        it.username,
                        it.password,
                        it.email,
                    )
                }
            }
    }

    override fun insertUser(user: UserEntity): Completable {
        return localDataSource
            .insert(user)
    }

}