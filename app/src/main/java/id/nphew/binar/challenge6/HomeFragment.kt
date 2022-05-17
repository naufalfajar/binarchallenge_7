package id.nphew.binar.challenge6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import id.nphew.binar.challenge5.model.MoviePopularItem
import id.nphew.binar.challenge5.service.ApiClient
import id.nphew.binar.challenge5.service.ApiService
import id.nphew.binar.challenge6.adapter.MovieAdapter
import id.nphew.binar.challenge6.databinding.FragmentHomeBinding
import id.nphew.binar.challenge6.datastore.DataStoreManager
import id.nphew.binar.challenge6.model.Status
import id.nphew.binar.challenge6.repo.AccountRepo
import id.nphew.binar.challenge6.repo.MovieRepo
import id.nphew.binar.challenge6.repo.viewModelsFactory
import id.nphew.binar.challenge6.viewmodel.LoginViewModel
import id.nphew.binar.challenge6.viewmodel.MovieViewModel
import id.nphew.binar.challenge6.viewmodel.UserViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val apiService: ApiService by lazy { ApiClient.instance }
    private lateinit var movieAdapter: MovieAdapter

    private val accRepo: AccountRepo by lazy { AccountRepo(requireContext()) }
    private val userViewModel: UserViewModel by viewModelsFactory { UserViewModel(accRepo) }

    private val movieRepo: MovieRepo by lazy { MovieRepo(apiService) }
    private val movieViewModel: MovieViewModel by viewModelsFactory { MovieViewModel(movieRepo) }

    private val pref: DataStoreManager by lazy { DataStoreManager(requireContext()) }
    private val loginModel: LoginViewModel by viewModelsFactory { LoginViewModel(pref) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeData()
        moveToProfile()
    }
    private fun initRecyclerView() {
        movieAdapter = MovieAdapter { id: Int, _: MoviePopularItem ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(id))
        }
        binding.rvMovie.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun observeData() {
        userViewModel.user.observe(viewLifecycleOwner) {
            val welcome = "Welcome, ${it?.username}!"
            binding.tvHomeWelcome.text = welcome
        }

        loginModel.getEmail().observe(viewLifecycleOwner) {
            userViewModel.getUser(it)
        }

        movieViewModel.getAllMovie().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    movieAdapter.updateData(it.data)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun moveToProfile() {
        binding.ivHomeProfile.setOnClickListener {
            it.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }
    }
}