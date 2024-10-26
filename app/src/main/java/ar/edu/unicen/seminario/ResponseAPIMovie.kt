package ar.edu.unicen.seminario

import ar.edu.unicen.seminario.entities.Movie

class ResponseAPIMovie(
    var error: String?=null,
    var result: List<Movie>?=null
) {
}