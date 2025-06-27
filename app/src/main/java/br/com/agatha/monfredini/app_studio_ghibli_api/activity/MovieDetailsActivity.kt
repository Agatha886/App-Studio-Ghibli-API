package br.com.agatha.monfredini.app_studio_ghibli_api.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.agatha.monfredini.app_studio_ghibli_api.adapter.CharacterAdapter
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ActivityMovieDetailsBinding
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie

class MovieDetailsActivity : AppCompatActivity() {

    private val viewModel: ListaPersonagensViewModel by viewModels()
    private lateinit var adapter: CharacterAdapter
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filme = intent.getSerializableExtra("filme") as? Movie ?: return
        binding.tituloFilme.text = filme.title

        adapter = CharacterAdapter()
        binding.recyclerPersonagens.layoutManager = LinearLayoutManager(this)
        binding.recyclerPersonagens.adapter = adapter

        binding.btnPreencherPersonagens.setOnClickListener {
            viewModel.carregarPersonagensDoFilme(filme)
        }

        viewModel.listaPersonagens.observe(this) { personagens ->
            adapter.submitList(personagens)
        }

        viewModel.erro.observe(this) { erro ->
            erro?.let {
                Toast.makeText(this, "Erro ao carregar personagens", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
