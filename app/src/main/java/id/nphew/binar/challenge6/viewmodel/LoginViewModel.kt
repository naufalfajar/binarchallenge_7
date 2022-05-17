package id.nphew.binar.challenge6.viewmodel

import androidx.lifecycle.*
import id.nphew.binar.challenge6.datastore.DataStoreManager
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: DataStoreManager): ViewModel() {

    val email = MutableLiveData<String>()

    fun saveData(id: Int, username: String, email: String, password: String) {
        viewModelScope.launch {
            pref.setLoggenInStatus(id, username, email, password)
        }
    }

    fun checkLogin(): LiveData<Boolean> {
        return pref.getLoggedInStatus().asLiveData()
    }

    fun getUserId(): LiveData<Int> {
        return pref.getUserId().asLiveData()
    }

    fun getPassword(): LiveData<String> {
        return pref.getPassword().asLiveData()
    }

    fun getEmail(): LiveData<String> {
        return pref.getEmail().asLiveData()
    }

    fun clearData() {
        viewModelScope.launch {
            pref.clearUserData()
        }
    }

}