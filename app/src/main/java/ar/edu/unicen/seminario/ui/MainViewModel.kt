package ar.edu.unicen.seminario.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminario.ResponseAPIMovie
import ar.edu.unicen.seminario.ResponseAPIMovieDetail
import ar.edu.unicen.seminario.data.MovieDetail
import ar.edu.unicen.seminario.data.MovieRepository
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

    private val _movies = MutableStateFlow<ResponseAPIMovie>(ResponseAPIMovie(null,null)) // Inicializa con un objeto vacío
    val movies = _movies.asStateFlow()

    private val _movieDetail = MutableStateFlow<ResponseAPIMovieDetail?>(ResponseAPIMovieDetail(null,null))
    var movieDetail= _movieDetail.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _errorMessageDetail = MutableStateFlow<String?>(null)
    val errorMessageDetail: StateFlow<String?> = _errorMessageDetail.asStateFlow()
    fun getAllMovies(language: String) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null


            val response = MovieRepository.getAllMovies(language)

            if (response?.result != null) {
                _movies.value = response
            } else {
                _errorMessage.value = response?.error
                _movies.value = ResponseAPIMovie(response?.error,null)
            }

            _loading.value = false
        }
    }
    fun getDetailMovie(language: String, id: Int) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessageDetail.value = null
            val movie = MovieRepository.getDetailMovie(language, id)
            if (movie?.MovieDetail != null) {
                _movieDetail.value = movie
            } else {
                _errorMessageDetail.value = movie?.error
                _movieDetail.value = ResponseAPIMovieDetail(movie?.error,null)
            }

            _loading.value = false
        }
    }

}