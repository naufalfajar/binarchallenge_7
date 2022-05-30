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
import id.nphew.binar.challenge6.adapter.MovieAdapter
import id.nphew.binar.challenge6.databinding.FragmentHomeBinding
import id.nphew.binar.challenge6.model.Status
import id.nphew.binar.challenge6.viewmodel.LoginViewModel
import id.nphew.binar.challenge6.viewmodel.MovieViewModel
import id.nphew.binar.challenge6.viewmodel.UserViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    private val userViewModel: UserViewModel by viewModel()
    private val movieViewModel: MovieViewModel by viewModel()
    private val loginModel: LoginViewModel by viewModel()

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