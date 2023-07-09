package rs.raf.nutritiontracker.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.entities.MealEntity
import rs.raf.nutritiontracker.data.models.entities.MealSavedEntity

@Dao
abstract class MealSavedDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: MealSavedEntity): Completable

    @Query("SELECT * FROM savedMeals WHERE user LIKE :name || '%'")
    abstract fun getByUser(name: String): Observable<List<MealSavedEntity>>

    @Query("DELETE FROM savedMeals WHERE mealId = :id")
    abstract fun deleteById(id: Long): Completable
}