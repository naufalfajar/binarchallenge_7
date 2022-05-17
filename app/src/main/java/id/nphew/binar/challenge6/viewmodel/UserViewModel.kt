package id.nphew.binar.challenge6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.nphew.binar.challenge6.database.User
import id.nphew.binar.challenge6.repo.AccountRepo
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: AccountRepo) : ViewModel() {
    val user = MutableLiveData<User?>()

    val registered = MutableLiveData<Boolean>()
    val notregistered = MutableLiveData<Boolean>()
    val validasiemailpassword = MutableLiveData<User?>()

    val insert = MutableLiveData<Boolean>()
    val update = MutableLiveData<Boolean>()
    val failupdate = MutableLiveData<Boolean>()

    fun getUser(email: String) {
        viewModelScope.launch {
            user.value = userRepository.getUser(email)
        }
    }

    fun checkRegistered(email: String, user: User) {
        viewModelScope.launch {
            val result = userRepository.getUser(email)
            if (result == null) {
                insertUser(user)
            } else {
                registered.value = true
            }
        }
    }

    fun checkLogin(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.getUser(email)
            if (result == null) {
                notregistered.value = true
            } else {
                if (result.email == email && result.password == password) {
                    validasiemailpassword.value = result
                }
            }
        }
    }

    private fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insert(user)
            insert.value = true
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            val result = userRepository.update(user)
            if (result != 0) {
                update.value = true
            } else {
                failupdate.value = true
            }
        }
    }
}