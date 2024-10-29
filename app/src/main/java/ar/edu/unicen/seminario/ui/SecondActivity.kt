package ar.edu.unicen.seminario.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import ar.edu.unicen.seminario.MoviesAdapter
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.data.Errors
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

        binding.navigationbutton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java) //intentar mandar un mensaje desde yo a main activity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP // clear top va desapilando las activities
            // flag single top si no esta la activity la crea, si esta usa la misma para guardar estado
            startActivity(intent)
            finish()
        }
    }


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
        }.launchIn(lifecycleScope)

        viewModel.movieDetail.onEach { movieDetail ->

            if(movieDetail!=null) {
                val concatenatedGenres = movieDetail.MovieDetail?.gender?.joinToString(",") ?: ""
                /*if(movieDetail.MovieDetail?.gender.isNullOrEmpty()){
                    binding.genres.text=""
                }
                if(movieDetail.MovieDetail?.vote_average ==null){
                    binding.rating.text=""
                }*/ //como arreglar esto?

                binding.movieTitle.text = movieDetail.MovieDetail?.title ?: ""
                binding.movieOverview.text = movieDetail.MovieDetail?.overview ?: ""
                binding.movieGenres.text = concatenatedGenres
                binding.voteAverage.text = movieDetail.MovieDetail?.vote_average?.toString() ?: ""

                if(movieDetail.MovieDetail?.picture!=null){
                    Glide.with(this)
                        .load("https://image.tmdb.org/t/p/w500/${movieDetail?.MovieDetail?.picture}") // Asegúrate de que 'posterPath' es el campo correcto
                        .transform(RoundedCorners(16))
                        .into(binding.moviePicture)
                }

            }
            else {
                binding.movieTitle.text = ""
                binding.movieOverview.text = ""
                binding.movieGenres.text = ""
                binding.voteAverage.text = ""
                binding.genres.text = " "
                binding.rating.text = " "
            }

        }.launchIn(lifecycleScope)// se ejecuta cuando la activity esta en ejecucion, en otros estados no

        viewModel.errorMessageDetail.onEach { errorMessage ->

            if(errorMessage!=null) {

                var error: Errors = Errors();
                if (errorMessage == error.NOINTERNET) {
                    Toast.makeText(this, R.string.nointernet, Toast.LENGTH_LONG).show()
                }
                else if(errorMessage== error.UNEXPECTED ){
                    Toast.makeText(this,R.string.errorunexpected, Toast.LENGTH_LONG).show()
                }
                else if(errorMessage== error.NOCONTENT){
                    Toast.makeText(this,R.string.nocontent, Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(lifecycleScope)

    }

}
