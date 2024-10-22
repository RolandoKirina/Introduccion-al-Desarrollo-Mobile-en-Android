package ar.edu.unicen.seminario.data
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
interface MovieApi {

    @GET("movie/popular")
    suspend fun getAllMovies(
        @Header("Authorization") token: String,
        @Query("language") language: String // Este es el idioma
    ): retrofit2.Response<MovieResponses>

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int,
        @Query("language") language: String
    ): retrofit2.Response<MovieDetailDto>
}