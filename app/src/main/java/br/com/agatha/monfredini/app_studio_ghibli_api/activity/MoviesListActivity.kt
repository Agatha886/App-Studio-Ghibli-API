package br.com.agatha.monfredini.app_studio_ghibli_api.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.agatha.monfredini.app_studio_ghibli_api.adapter.MoviesAdapter
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ActivityMovieListBinding

class MoviesListActivity : AppCompatActivity() {

    private val viewModel: ListaFilmesViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter
    private lateinit var binding: ActivityMovieListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MoviesAdapter { filme ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("filme", filme)
            startActivity(intent)
        }

        binding.recyclerFilmes.layoutManager = LinearLayoutManager(this)
        binding.recyclerFilmes.adapter = adapter

        binding.btnPreencherLista.setOnClickListener {
            viewModel.carregarFilmes()
        }

        viewModel.listaFilmes.observe(this) { filmes ->
            adapter.submitList(filmes)
        }

        viewModel.erro.observe(this) { erro ->
            erro?.let {
                Toast.makeText(this, "Erro ao carregar filmes", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
