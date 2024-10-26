package ar.edu.unicen.seminario.data

import ar.edu.unicen.seminario.ResponseAPIMovie
import ar.edu.unicen.seminario.ResponseAPIMovieDetail

import javax.inject.Inject

class MovieRepository @Inject constructor (
    private val movieLocalDataSource: MovieLocalDataSource
){

    suspend fun getAllMovies(languague:String): ResponseAPIMovie? {
        return movieLocalDataSource.getAllMovies(languague);
    }


    suspend fun getDetailMovie(language:String,id:Int): ResponseAPIMovieDetail?{
        return movieLocalDataSource.getMovieById(language,id);
    }



}
