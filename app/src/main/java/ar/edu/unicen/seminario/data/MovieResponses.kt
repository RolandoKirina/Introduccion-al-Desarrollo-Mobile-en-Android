package ar.edu.unicen.seminario.data
import ar.edu.unicen.seminario.entities.Gender
import ar.edu.unicen.seminario.entities.Movie
import com.google.gson.annotations.SerializedName;


data class MovieResponses(
    //results es el arreglo de arreglos de peliculas de la api
    @SerializedName("results") val movies: List<MovieDto>
) {
}