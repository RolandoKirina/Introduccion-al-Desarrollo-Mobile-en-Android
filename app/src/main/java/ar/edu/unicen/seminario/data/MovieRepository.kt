package ar.edu.unicen.seminario.data

import ar.edu.unicen.seminario.entities.Gender
import ar.edu.unicen.seminario.entities.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor (
    private val movieLocalDataSource: MovieLocalDataSource
){

    suspend fun getAllMovies(languague:String): List<Movie>? {
        return movieLocalDataSource.getAllMovies(languague);
    }


    suspend fun getDetailMovie(language:String,id:Int): MovieDetail?{
        return movieLocalDataSource.getMovieById(language,id);
    }



}
