package rs.raf.nutritiontracker.data.repositories.implementation

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasources.local.CategoryDao
import rs.raf.nutritiontracker.data.datasources.remote.CategoryService
import rs.raf.nutritiontracker.data.models.Category
import rs.raf.nutritiontracker.data.models.entities.CategoryEntity
import rs.raf.nutritiontracker.data.models.Resource
import rs.raf.nutritiontracker.data.repositories.specification.CategoryRepository
import timber.log.Timber

class CategoryRepositoryImpl(
    private val localDataSource: CategoryDao,
    private val remoteDataSource: CategoryService
) : CategoryRepository {
    // TODO: Dodati fetchAll metodu koja skuplja podatke sa interneta i stavlja ih u bazu
        override fun fetchAll(): Observable<Resource<Unit>> {
            return remoteDataSource
                .getAllCategories()
                .doOnNext {
                    Timber.e("Upis u bazu")
                    println("AAAAAAAAA")
                    val entities = it.categories.map {
                        CategoryEntity(
                            it.idCategory,
                            it.strCategory,
                            it.strCategoryThumb,
                            it.strCategoryDescription
                        )
                    }
                    localDataSource.deleteAndInsertAll(entities)
                }
                .map {
                    Resource.Success(Unit)
                }
        }

    override fun getAll(): Observable<List<Category>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Category(it.idCategory, it.strCategory,
                        it.strCategoryThumb, it.strCategoryDescription)
                }
            }
    }

    override fun getAllByName(name: String): Observable<List<Category>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    Category(it.idCategory, it.strCategory,
                        it.strCategoryThumb, it.strCategoryDescription)
                }
            }
    }

    override fun insert(category: Category): Completable {
        TODO("Not yet implemented")
    }
}