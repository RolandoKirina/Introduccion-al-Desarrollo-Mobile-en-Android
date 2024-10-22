package ar.edu.unicen.seminario.data

import ar.edu.unicen.seminario.entities.Gender
import ar.edu.unicen.seminario.entities.Movie
import com.google.gson.annotations.SerializedName


data class MovieDetailDto (@SerializedName("id") val id: Int,
                           @SerializedName("title") val title: String,
                           @SerializedName("poster_path") val picture: String,
                           @SerializedName("overview") val overview: String,
                           @SerializedName("genres") val genres: List<Gender>,
                           @SerializedName("vote_average") val vote_average: Float) {

    fun toMovieDetail(): MovieDetail {
        return MovieDetail(
            id = id,
            title = title,
            picture = picture,
            overview = overview,
            gender = genres.map{it.name},
            vote_average = vote_average
        );
    }

}