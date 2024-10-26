package ar.edu.unicen.seminario.data

import ar.edu.unicen.seminario.entities.Gender

class MovieDetail(
    val id: Int,
    val title: String,
    val picture: String?,
    val overview: String,
    val gender: List<String>,
    val vote_average: Float

) {

}