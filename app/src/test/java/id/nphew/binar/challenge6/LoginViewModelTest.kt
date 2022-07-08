package id.nphew.binar.challenge6

import id.nphew.binar.challenge6.datastore.DataStoreManager
import id.nphew.binar.challenge6.repo.AccountRepo
import id.nphew.binar.challenge6.viewmodel.LoginViewModel
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {
    lateinit var viewModel: LoginViewModel
    lateinit var pref: DataStoreManager
    lateinit var userRepository: AccountRepo


    @Before
    fun setUp() {
        pref = mockk()
        userRepository = mockk()
        viewModel = LoginViewModel(pref)
    }

    @Test
    fun checkLogin() {
        val result = viewModel.checkLogin()
        val login = false
        Assert.assertEquals(login, result)
    }

    @Test
    fun getEmail() {
        val result = viewModel.getEmail()
        val email = "default"
        Assert.assertEquals(email, result)
    }
}
