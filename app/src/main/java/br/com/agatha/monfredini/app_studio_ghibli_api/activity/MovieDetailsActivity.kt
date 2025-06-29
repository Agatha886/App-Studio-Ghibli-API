package br.com.agatha.monfredini.app_studio_ghibli_api.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.agatha.monfredini.app_studio_ghibli_api.adapter.CharacterAdapter
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ActivityMovieDetailsBinding
import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.CharactersListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {

    private val viewModel: CharactersListViewModel by viewModel()
    private lateinit var adapter: CharacterAdapter
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getSerializableExtra("movie") as? Movie ?: return
        binding.tituloFilme.text = movie.title

        adapter = CharacterAdapter()
        binding.recyclerPersonagens.layoutManager = LinearLayoutManager(this)
        binding.recyclerPersonagens.adapter = adapter
        viewModel.whenFail = {
            Toast.makeText(this, "Cannot Get Characeters", Toast.LENGTH_SHORT).show()
        }
        viewModel.characterList.observe(this) { characters ->
            adapter.submitList(characters)
            logInfo("Movies List : $characters")
        }

        binding.btnPreencherPersonagens.setOnClickListener {
            viewModel.getCharacterByMovie(movie)
        }
    }
}
