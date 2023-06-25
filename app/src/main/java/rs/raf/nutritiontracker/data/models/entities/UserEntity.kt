package rs.raf.nutritiontracker.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Long,
    val username: String,
    val password: String,
    val email: String,
) {

}