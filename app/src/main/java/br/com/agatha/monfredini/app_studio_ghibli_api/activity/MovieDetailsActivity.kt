package br.com.agatha.monfredini.app_studio_ghibli_api.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.agatha.monfredini.app_studio_ghibli_api.adapter.ListCharactersAdapter
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ActivityMovieDetailsBinding
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.DetailsMovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {

    private val viewModel: DetailsMovieViewModel by viewModel()
    private lateinit var adapter: ListCharactersAdapter
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getSerializableExtra("movie") as? Movie ?: return
        binding.movieTitle.text = movie.title

        adapter = ListCharactersAdapter { characeter ->
            val intent = Intent(this, CharacterDetailActivity::class.java)
            intent.putExtra("character", characeter)
            startActivity(intent)
        }

        binding.recyclerCharacters.layoutManager = LinearLayoutManager(this)
        binding.recyclerCharacters.adapter = adapter

        viewModel.characterList.observe(this) { characters ->
            adapter.submitList(characters)
        }

        binding.btnGetCharacters.setOnClickListener {
            viewModel.getCharacterByMovie(movie) {
                Toast.makeText(this, "Cannot Get Characters", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
