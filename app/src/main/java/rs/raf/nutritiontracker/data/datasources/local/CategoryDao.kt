package rs.raf.nutritiontracker.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.models.entities.CategoryEntity
import rs.raf.nutritiontracker.data.models.CategoryWithMeals

@Dao
abstract class CategoryDao {
    //Completable znaci da li je uspesno zavrsio ubacivanje entiteta
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCategory(entity : CategoryEntity): Completable

    // Single list long slicno kao observable za koji imamo tri bloka (uspesno, ne, i sta se uvek izvrsi)
    // Samo ovde imamo dva bloka koja treba da obradimo (ili je uspelo, ili nije)
    // Room sam prepozna da treba davrati listu id-jeva
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAllCategories(entities: List<CategoryEntity>): Completable

    @Query("SELECT * FROM categories")
    abstract fun getAll(): Observable<List<CategoryEntity>>

    @Query("DELETE FROM categories")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<CategoryEntity>) {
        deleteAll()
        insertAllCategories(entities).blockingAwait()
    }

    @Query("SELECT * FROM categories WHERE strCategory LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<CategoryEntity>>

    @Query("SELECT * FROM categories")
    abstract fun getCategoriesWithMeals(): List<CategoryWithMeals>
}