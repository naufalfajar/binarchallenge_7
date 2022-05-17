package id.nphew.binar.challenge6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import id.nphew.binar.challenge6.databinding.FragmentSplashScreenBinding
import id.nphew.binar.challenge6.datastore.DataStoreManager
import id.nphew.binar.challenge6.repo.viewModelsFactory
import id.nphew.binar.challenge6.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

class SplashScreen : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    private val pref: DataStoreManager by lazy { DataStoreManager(requireContext()) }
    private val loginModel: LoginViewModel by viewModelsFactory { LoginViewModel(pref) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return  binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val splashTime: Long = 3000

        lifecycleScope.launchWhenCreated {
            splashToLogin(splashTime)
        }
    }
    private suspend fun splashToLogin(time: Long){
        delay(time)
        observe()
    }

    private fun observe() {
        loginModel.checkLogin().observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(SplashScreenDirections.actionSplashScreenToHomeFragment())
            } else{
                findNavController().navigate(SplashScreenDirections.actionSplashScreenToLoginFragment())
            }
        }
    }
}