package id.nphew.binar.challenge6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import id.nphew.binar.challenge6.databinding.FragmentLoginBinding
import id.nphew.binar.challenge6.datastore.DataStoreManager
import id.nphew.binar.challenge6.repo.AccountRepo
import id.nphew.binar.challenge6.repo.viewModelsFactory
import id.nphew.binar.challenge6.viewmodel.LoginViewModel
import id.nphew.binar.challenge6.viewmodel.UserViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginModel: LoginViewModel by viewModelsFactory { LoginViewModel(pref) }
    private val viewModel: UserViewModel by viewModelsFactory { UserViewModel(accRepo) }

    private val accRepo: AccountRepo by lazy { AccountRepo(requireContext()) }
    private val pref: DataStoreManager by lazy { DataStoreManager(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onStart() {
//        super.onStart()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        login()
        noAccount()
    }

    private fun login(){
        binding.apply {
            btnLoginLogin.setOnClickListener {
                val email = etLoginEmail.text.toString()
                val password = etLoginPassword.text.toString()
                if (validateLoginInput(email, password)) {
                    viewModel.checkLogin(email, password)
                }
            }
        }
    }

    private fun validateLoginInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.etLoginEmail.error = "Silahkan masukkan email!"
            return false
        }
        if (password.isEmpty()) {
            binding.etLoginPassword.error = "Silahkan masukkan password!"
            return false
        }
        return true
    }

    private fun observeData() {
        viewModel.notregistered.observe(viewLifecycleOwner) {
            createToast("Email belum terdaftar!").show()
        }

        viewModel.validasiemailpassword.observe(viewLifecycleOwner) {
            if (it != null) {
                loginModel.saveData(it.id!!, it.username, it.email, it.password)
            }
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }
    }

    private fun noAccount(){
        binding.etLoginTdkpunyaakun.setOnClickListener{
            it.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }
    private fun createToast(msg: String): Toast {
        return Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG)
    }
}