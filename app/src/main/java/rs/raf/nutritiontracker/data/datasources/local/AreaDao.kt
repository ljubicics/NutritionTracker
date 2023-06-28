package rs.raf.nutritiontracker.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.entities.AreaEntity
import rs.raf.nutritiontracker.data.models.entities.CategoryEntity

@Dao
abstract class AreaDao {

    @Query("SELECT * FROM areas")
    abstract fun getAll(): Observable<List<AreaEntity>>

    @Query("DELETE FROM areas")
    abstract fun deleteAll()

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAllAreas(entities: List<AreaEntity>): Completable

    @Transaction
    open fun deleteAndInsertAll(entities: List<AreaEntity>) {
        deleteAll()
        insertAllAreas(entities).blockingAwait()
    }
}