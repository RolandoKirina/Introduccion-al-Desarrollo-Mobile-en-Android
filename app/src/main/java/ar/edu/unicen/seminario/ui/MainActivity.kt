package ar.edu.unicen.seminario.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import ar.edu.unicen.seminario.MoviesAdapter
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.data.Errors
import ar.edu.unicen.seminario.databinding.ActivityMainBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout y configurar binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        suscribeToUi()
        suscribeToViewModel()
    }

    private fun suscribeToUi() {
        val language = getString(R.string.language)
        viewModel.getAllMovies(language)
    }

    private fun suscribeToViewModel() {
        viewModel.loading.onEach { loading ->
            binding.progressbar.visibility =
                if (loading) View.VISIBLE else View.GONE
        }.launchIn(lifecycleScope)

        viewModel.movies.onEach { response ->
            val movieList = response.result ?: emptyList()
            Log.d("resultado", response.result.toString())
            binding.listMovies.adapter = MoviesAdapter(
                movies = movieList,
                onMoviesClick = { movie ->
                    val intent = Intent(this, SecondActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        putExtra("id", movie.id) // Paso el id de la película
                    }
                    startActivity(intent)
                }
            )
        }.launchIn(lifecycleScope)

        viewModel.errorMessage.onEach { errorMessage ->

            var error: Errors= Errors();
            if (errorMessage == error.NOINTERNET) {
                Toast.makeText(this, R.string.nointernet, Toast.LENGTH_SHORT).show()
            }
            else if(errorMessage== error.UNEXPECTED ){
                Toast.makeText(this,R.string.errorunexpected,Toast.LENGTH_SHORT).show()
            }
            else if(errorMessage== error.NOCONTENT){
                Toast.makeText(this,R.string.nocontent,Toast.LENGTH_SHORT).show()
            }

        }.launchIn(lifecycleScope)

    }
}
