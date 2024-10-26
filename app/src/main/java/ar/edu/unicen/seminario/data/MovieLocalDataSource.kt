package ar.edu.unicen.seminario.data

import ar.edu.unicen.seminario.ResponseAPIMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import android.content.Context
import android.util.Log
import ar.edu.unicen.seminario.ResponseAPIMovieDetail
import dagger.hilt.android.qualifiers.ApplicationContext


class MovieLocalDataSource  @Inject constructor(
    //private val sharedPreferences: SharedPreferences,
    private val movieApi: MovieApi,
    private val tokenKey: String,
    @ApplicationContext private val context: Context // preguntar por esto

) {

    suspend fun getAllMovies(language: String): ResponseAPIMovie? {
        var err:Errors= Errors()
        return withContext(Dispatchers.IO) {
            val responseAPIMovie = ResponseAPIMovie()
            try {
                val result = movieApi.getAllMovies("Bearer $tokenKey", language)

                if (result.isSuccessful) {
                    if(result.body()?.movies.isNullOrEmpty()){
                        responseAPIMovie?.error = err.NOCONTENT
                    }
                    responseAPIMovie?.result = result.body()?.movies?.map { it.toMovie() }
                }

            }
            catch (e: IOException) {
                responseAPIMovie?.error = err.NOINTERNET
            }
            catch (e: Exception) {
                responseAPIMovie?.error = err.UNEXPECTED
                e.printStackTrace()
            }
            Log.d("resultado api", responseAPIMovie?.result.toString())
            return@withContext responseAPIMovie
        }
    }

    suspend fun getMovieById(language: String, id: Int): ResponseAPIMovieDetail? {
        var err: Errors = Errors()
        val responseAPIMovieDetail = ResponseAPIMovieDetail(null,null)
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getMovieById("Bearer $tokenKey", id, language)
                val movieResponse = response.body()
                if (response.isSuccessful) {
                    if (response.body() == null) {
                        responseAPIMovieDetail?.error = err.NOCONTENT
                    } else {
                        responseAPIMovieDetail.MovieDetail = movieResponse?.toMovieDetail()
                        responseAPIMovieDetail.error= null;
                    }
                }

            } catch (e: IOException) {
                responseAPIMovieDetail?.error = err.NOINTERNET
                return@withContext responseAPIMovieDetail
            } catch (e: Exception) {
                responseAPIMovieDetail?.error = err.UNEXPECTED
                e.printStackTrace()
            }
            return@withContext responseAPIMovieDetail
        }
    }
}


