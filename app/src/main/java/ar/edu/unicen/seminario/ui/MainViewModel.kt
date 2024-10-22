package ar.edu.unicen.seminario.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.data.MovieDetail
import ar.edu.unicen.seminario.data.MovieRepository
import ar.edu.unicen.seminario.entities.Gender
import ar.edu.unicen.seminario.entities.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    //En los view model siempre accedemos al repository
    private val MovieRepository: MovieRepository,

) : ViewModel() {

    private val _loading = MutableStateFlow<Boolean>(true)
    val loading : StateFlow<Boolean> = _loading.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>?>(null)
    val movies = _movies.asStateFlow();


    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    var movieDetail: StateFlow<MovieDetail?> = _movieDetail.asStateFlow()

    fun getAllMovies(language: String){
        viewModelScope.launch {
            _loading.value=true
            _movies.value= emptyList()
            val movies = MovieRepository.getAllMovies(language)
            _movies.value = movies ?: emptyList()
            _loading.value=false

        }
    }
    fun getDetailMovie(language: String, id: Int) {
        viewModelScope.launch {
            _loading.value = true
            val movie = MovieRepository.getDetailMovie(language, id)
            _movieDetail.value = movie
            _loading.value = false
        }
    }

}