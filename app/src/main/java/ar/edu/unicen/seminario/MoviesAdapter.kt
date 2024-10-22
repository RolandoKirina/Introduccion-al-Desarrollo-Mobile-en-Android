package ar.edu.unicen.seminario

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unicen.seminario.databinding.ItemMovieBinding
import ar.edu.unicen.seminario.entities.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class MoviesAdapter (
    private val movies: List<Movie>,
    // es un callback para manejar los clicks en cada item
    private val onMoviesClick:(Movie)-> Unit): RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>(){

        inner class MovieViewHolder(
            private val binding: ItemMovieBinding
        ): RecyclerView.ViewHolder(binding.root){

            fun bind(movie:Movie){
                binding.movieTitle.text = movie.title

                if(movie.picture!=null){
                    Glide.with(itemView.context)
                        //imagen de la primer actividad
                        .load("https://image.tmdb.org/t/p/w500/" + movie.picture) // url de imagen
                        .transform(RoundedCorners(16))
                        .into(binding.movieImage)
                }

                binding.root.setOnClickListener(){
                    onMoviesClick(movie)
                }
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
       val layaoutInflater : LayoutInflater= LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layaoutInflater,parent,false) //levanto el xml item movie
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }
}