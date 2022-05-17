package id.nphew.binar.challenge6.repo

import android.content.Context
import id.nphew.binar.challenge6.database.User
import id.nphew.binar.challenge6.database.UserDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepo(context: Context) {
    private val db = UserDB.getInstance(context)

    suspend fun getUser(email: String) = withContext(Dispatchers.IO) {
        db?.userDao()?.getUser(email)
    }

    suspend fun insert(user: User) = withContext(Dispatchers.IO) {
        db?.userDao()?.insertUser(user)
    }

    suspend fun update(user: User) = withContext(Dispatchers.IO) {
        db?.userDao()?.updateUser(user)
    }
}