package id.nphew.binar.challenge6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import id.nphew.binar.challenge6.database.User
import id.nphew.binar.challenge6.databinding.FragmentProfileBinding
import id.nphew.binar.challenge6.datastore.DataStoreManager
import id.nphew.binar.challenge6.repo.AccountRepo
import id.nphew.binar.challenge6.repo.viewModelsFactory
import id.nphew.binar.challenge6.viewmodel.LoginViewModel
import id.nphew.binar.challenge6.viewmodel.UserViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by sharedViewModel()
    private val loginModel: LoginViewModel by sharedViewModel()

    private var userId: Int = 0
    private var email: String = "default"
    private var password: String = "default"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logout()
        updateProfile()
        observeData()
    }

    private fun logout(){
        binding.btnProfileLogout.setOnClickListener {
            loginModel.clearData()
            it.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
        }
    }

    private fun updateProfile() {
        binding.apply {
            btnProfileUpdate.setOnClickListener {
                val username = etProfileUsername.text.toString()
                val fullname = etProfileFullname.text.toString()
                val birthdate = etProfileBirthdate.text.toString()
                val address = etProfileAddress.text.toString()

                val user = User(userId, username, email, password, fullname, birthdate, address)
                viewModel.updateUser(user)
            }
        }
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    etProfileUsername.setText(it.username)
                    etProfileFullname.setText(it.name)
                    etProfileBirthdate.setText(it.birthdate)
                    etProfileAddress.setText(it.alamat)
                }
            }
        }
        loginModel.getEmail().observe(viewLifecycleOwner) {
            viewModel.getUser(it)
            this.email = it
        }

        loginModel.getUserId().observe(viewLifecycleOwner) {
            this.userId = it
        }

        loginModel.getPassword().observe(viewLifecycleOwner) {
            this.password = it
        }
        viewModel.update.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Update Berhasil!", Toast.LENGTH_SHORT).show()
        }

        viewModel.failupdate.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Update Gagal!", Toast.LENGTH_SHORT).show()
        }
    }
}