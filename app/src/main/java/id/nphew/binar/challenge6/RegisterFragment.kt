package id.nphew.binar.challenge6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import id.nphew.binar.challenge6.database.User
import id.nphew.binar.challenge6.databinding.FragmentRegisterBinding
import id.nphew.binar.challenge6.repo.AccountRepo
import id.nphew.binar.challenge6.repo.viewModelsFactory
import id.nphew.binar.challenge6.viewmodel.UserViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val accRepo: AccountRepo by lazy { AccountRepo(requireContext()) }
    private val viewModel: UserViewModel by viewModelsFactory { UserViewModel(accRepo) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerUser()
        observeData()
    }

    private fun registerUser() {
        binding.apply {
            btnRegisterDaftar.setOnClickListener {
                val username = etRegisterUsername.text.toString()
                val email = etLoginEmail.text.toString()
                val password = etLoginPassword.text.toString()
                val conf = etLoginConfirmpassword.text.toString()

                if (checkField(username, email, password, conf)) {
                    val user = User(null, username, email, password, "", "", "")
                    viewModel.checkRegistered(email, user)
                }
            }
        }
    }

    private fun checkField(username: String, email: String, password: String, conf: String): Boolean {
        if (username.isEmpty()) {
            binding.etRegisterUsername.error = "Silahkan masukkan username!"
            return false
        }

        if (email.isEmpty()) {
            binding.etLoginEmail.error = "Silahkan masukkan email!"
            return false
        }

        if (password.isEmpty()) {
            binding.etLoginPassword.error = "Silahkan masukkan password!"
            return false
        }

        if (conf.isEmpty()) {
            binding.etLoginConfirmpassword.error = "Silahkan masukkan konfirmasi password!"
            return false
        }

        if (password != conf) {
            binding.etLoginConfirmpassword.error = "Password tidak cocok!"
            return false
        }
        return true
    }

    private fun observeData() {
        viewModel.insert.observe(viewLifecycleOwner) {
            createToast("Registrasi Sukses!").show()
            binding.root.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        viewModel.registered.observe(viewLifecycleOwner) {
            createToast("Email sudah terdaftar!").show()
        }
    }

    private fun createToast(msg: String): Toast {
        return Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG)
    }

}