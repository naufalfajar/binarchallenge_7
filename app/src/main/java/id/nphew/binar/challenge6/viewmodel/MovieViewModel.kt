package id.nphew.binar.challenge6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.nphew.binar.challenge6.model.Resource
import id.nphew.binar.challenge6.repo.MovieRepo
import kotlinx.coroutines.Dispatchers


class MovieViewModel(private val repository: MovieRepo) : ViewModel() {
    fun getAllMovie() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getMovie("1277127d2eed7437027f5d5952fa28c2")))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getDetailMovie(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    repository.getDetailMovie(
                        movieId,
                        "1277127d2eed7437027f5d5952fa28c2"
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
}