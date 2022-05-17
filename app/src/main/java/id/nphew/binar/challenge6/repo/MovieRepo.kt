package id.nphew.binar.challenge6.repo

import id.nphew.binar.challenge5.service.ApiService

class MovieRepo(private val apiService: ApiService) {
    suspend fun getMovie(key: String) = apiService.getAllMovie(key)

    suspend fun getDetailMovie(movieId: Int, key: String) = apiService.getMovieDetail(movieId, key)
}