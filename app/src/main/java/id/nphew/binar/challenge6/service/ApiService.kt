package id.nphew.binar.challenge5.service

import id.nphew.binar.challenge5.model.MoviePopular
import id.nphew.binar.challenge5.model.MoviePopularItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getAllMovie(@Query("api_key") key: String): MoviePopular

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") key: String
    ): MoviePopularItem

}