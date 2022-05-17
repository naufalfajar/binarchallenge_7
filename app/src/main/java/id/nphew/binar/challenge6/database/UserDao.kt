package id.nphew.binar.challenge6.database

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAllUser(): List<User>

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUser(email: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Update
    fun updateUser(user: User): Int

    @Delete
    fun deleteUser(user: User): Int
}