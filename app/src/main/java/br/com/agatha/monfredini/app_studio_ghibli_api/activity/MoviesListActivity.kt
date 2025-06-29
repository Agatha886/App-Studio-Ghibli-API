package br.com.agatha.monfredini.app_studio_ghibli_api.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.agatha.monfredini.app_studio_ghibli_api.adapter.MoviesAdapter
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.MoviesListViewModel
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ActivityMovieListBinding
import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi.logInfo
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListActivity : AppCompatActivity() {

    private val viewModel: MoviesListViewModel by viewModel()
    private lateinit var adapter: MoviesAdapter
    private lateinit var binding: ActivityMovieListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MoviesAdapter { movie ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }

        binding.recyclerFilmes.layoutManager = LinearLayoutManager(this)
        binding.recyclerFilmes.adapter = adapter

        viewModel.whenFail = {
            Toast.makeText(this, "Cannot get Movies", Toast.LENGTH_SHORT).show()
        }

        viewModel.movies.observe(this) { filmes ->
            adapter.submitList(filmes)
        }

        binding.btnPreencherLista.setOnClickListener {
            viewModel.getMovies()
        }
    }
}
