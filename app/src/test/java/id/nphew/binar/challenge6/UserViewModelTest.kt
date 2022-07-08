package id.nphew.binar.challenge6

import id.nphew.binar.challenge6.repo.AccountRepo
import id.nphew.binar.challenge6.viewmodel.UserViewModel
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserViewModelTest {
        lateinit var viewModel: UserViewModel
        lateinit var accRepo: AccountRepo

        @Before
        fun setUp() {
            accRepo = mockk()
            viewModel = UserViewModel(accRepo)
        }

        @Test
        fun checkLogin() {
            val email = ""
            val password = ""
            viewModel.checkLogin(email, password)
            val result = viewModel.notregistered
            Assert.assertEquals(true, result)
        }
}