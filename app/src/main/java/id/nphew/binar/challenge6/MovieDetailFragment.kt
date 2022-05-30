package id.nphew.binar.challenge6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import id.nphew.binar.challenge5.service.ApiClient
import id.nphew.binar.challenge5.service.ApiService
import id.nphew.binar.challenge6.databinding.FragmentMovieDetailBinding
import id.nphew.binar.challenge6.model.Status
import id.nphew.binar.challenge6.repo.MovieRepo
import id.nphew.binar.challenge6.repo.viewModelsFactory
import id.nphew.binar.challenge6.viewmodel.MovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

//    private val apiService: ApiService by lazy { ApiClient.instance }

//    private val movieRepo: MovieRepo by lazy { MovieRepo(apiService) }
    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = MovieDetailFragmentArgs.fromBundle(arguments as Bundle).movieId
        observeData(movieId)
    }

    private fun observeData(movieId: Int) {
        movieViewModel.getDetailMovie(movieId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.apply {
                        Glide.with(requireContext())
                            .load("https://www.themoviedb.org/t/p/w220_and_h330_face/" + it.data!!.posterPath)
                            .into(ivPoster)
                        tvReleaseDate.text = it.data.releaseDate
                        tvTitle.text = it.data.title
                        tvOverview.text = it.data.overview
                        pbMovie.isVisible = false
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }
}