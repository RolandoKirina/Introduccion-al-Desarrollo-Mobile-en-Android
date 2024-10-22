package ar.edu.unicen.seminario.data

import android.content.SharedPreferences
import android.util.Log
import ar.edu.unicen.seminario.entities.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieLocalDataSource  @Inject constructor(
    //private val sharedPreferences: SharedPreferences,
    private val movieApi: MovieApi,
    private val tokenKey: String
) {


    suspend fun getAllMovies(language: String): List<Movie>? {
        return withContext(Dispatchers.IO) {
            //sharedPreferences.getInt("counter",0); esto es para guardar datos localmente...
            try {
                val response = movieApi.getAllMovies("Bearer $tokenKey", language)
                return@withContext response.body()?.movies?.map { it.toMovie() }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun getMovieById(language: String, id: Int): MovieDetail? {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getMovieById("Bearer $tokenKey", id, language)

                    val movieResponse = response.body()
                    // Verifica si el objeto movie no es nulo
                    return@withContext movieResponse?.toMovieDetail()
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }
}
