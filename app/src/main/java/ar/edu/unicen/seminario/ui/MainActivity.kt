package ar.edu.unicen.seminario.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.seminario.Genre
import ar.edu.unicen.seminario.MoviesAdapter
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.databinding.ActivityMainBinding
import ar.edu.unicen.seminario.entities.Gender
import ar.edu.unicen.seminario.entities.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Inflar el layout y configurar binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        suscribeToUi();
        suscribeToViewModel()

    }

    private fun suscribeToUi(){
        val language = getString(R.string.language)

        viewModel.getAllMovies(language);
    }
    private fun suscribeToViewModel(){

        viewModel.loading.onEach { loading ->
            if (loading) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE
            }
        }.launchIn(lifecycleScope) // se ejecuta cuando la activity esta en ejecucion, en otros estados no

        viewModel.movies.onEach { movies ->
            val movieList = movies ?: emptyList()
            binding.listMovies.adapter = MoviesAdapter(
                movies = movieList,
                onMoviesClick = { movie ->
                    lifecycleScope.launch {
                        val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            // Paso el id de la movie
                            putExtra("id", movie.id)
                        }
                        startActivity(intent)
                    }
                }
            )
        }.launchIn(lifecycleScope)
    }

}
