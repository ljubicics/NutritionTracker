package rs.raf.nutritiontracker.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.MealForCategoryEntity

@Dao
abstract class MealsForCategoryDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: MealForCategoryEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<MealForCategoryEntity>): Completable

    @Query("SELECT * FROM mealsForCategory")
    abstract fun getAll(): Observable<List<MealForCategoryEntity>>

    @Query("SELECT * FROM mealsForCategory WHERE strCategory LIKE :category")
    abstract fun getAllForCategory(category: String): Observable<List<MealForCategoryEntity>>

    @Query("DELETE FROM mealsForCategory")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<MealForCategoryEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("SELECT * FROM mealsForCategory WHERE strMeal LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<MealForCategoryEntity>>
}