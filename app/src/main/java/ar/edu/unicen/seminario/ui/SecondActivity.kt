package ar.edu.unicen.seminario.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import ar.edu.unicen.seminario.MoviesAdapter
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.data.ddsad
import ar.edu.unicen.seminario.databinding.SecondActivityBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SecondActivity : AppCompatActivity()  {

    private lateinit var binding: SecondActivityBinding
    private  val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState) //primera etapa de ciclo de vida del activity on create
        enableEdgeToEdge()//pantalla incluida con las barras de arriba y abajo
        binding = SecondActivityBinding.inflate(layoutInflater) //cuando se inicializa un xml se dice que se infla
        setContentView(binding.root)

        suscribeToUi();
        suscribeToViewModel()

        viewModel.movieDetail.onEach { movieDetail ->


                val genrestext:String = getString(R.string.genrestext);
                val averagetext:String = getString(R.string.averagetext)
                val concatenatedGenres = movieDetail?.gender?.joinToString(",")
                binding.movieTitle.text = movieDetail?.title;
                binding.movieOverview.text = movieDetail?.overview;
                binding.movieGenres.text = genrestext + " " + concatenatedGenres;
                binding.voteAverage.text = averagetext + " " + movieDetail?.vote_average.toString();
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/${movieDetail?.picture}") // Asegúrate de que 'posterPath' es el campo correcto
                    .transform(RoundedCorners(16))
                    .into(binding.moviePicture)
            }.launchIn(lifecycleScope)

        binding.navigationbutton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java) //intentar mandar un mensaje desde yo a main activity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP // clear top va desapilando las activities
            // flag single top si no esta la activity la crea, si esta usa la misma para guardar estado
            //TODO aca va un finish
            startActivity(intent)
        }
    }

    val prueba: ddsad=ddsad.NOCONTENT //una prueba.
    private fun suscribeToUi(){
        val id = intent.getIntExtra("id",0);
        val language = getString(R.string.language)
        viewModel.getDetailMovie(language,id);

    }
    private fun suscribeToViewModel() {

        viewModel.loading.onEach { loading ->
            if (loading) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE
            }
        }.launchIn(lifecycleScope) // se ejecuta cuando la activity esta en ejecucion, en otros estados no
    }
}
