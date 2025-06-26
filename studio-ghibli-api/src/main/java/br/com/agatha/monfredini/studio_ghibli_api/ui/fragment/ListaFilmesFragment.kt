package br.com.agatha.monfredini.studioghibli.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.agatha.monfredini.studioghibli.R
import br.com.agatha.monfredini.studioghibli.R.layout
import br.com.agatha.monfredini.studioghibli.repository.ListaFilmesRepository
import br.com.agatha.monfredini.studioghibli.ui.model.Filme
import br.com.agatha.monfredini.studioghibli.ui.recycler.adapter.ListaFilmesAdapter
import br.com.agatha.monfredini.studioghibli.viewmodel.ListaFilmesViewModel
import br.com.agatha.monfredini.studioghibli.viewmodel.ListaPersonagensViewModel
import kotlinx.android.synthetic.main.lista_filmes_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListaFilmesFragment : Fragment() {

    private val viewModel: ListaFilmesViewModel by viewModel()

    private val viewModelTeste:ListaFilmesViewModel by lazy {
        ListaFilmesViewModel(ListaFilmesRepository())
    }

    private val adapter by lazy {
        context?.let {
            ListaFilmesAdapter(contex = it)
        } ?: throw IllegalArgumentException("Contexto inválido")
    }

    var quandoItemFragmentClicado: (filme: Filme) -> Unit = {}
    var quandoBuscaTerminada: (filmes: List<Filme>) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.lista_filmes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Lista de Filmes"
        lista_filmes_recyclerview.adapter = adapter

        viewModel.quandoFalha = {
            lista_filmes_recyclerview.visibility = View.INVISIBLE
            lista_filmes_imagem_erro.layoutParams.height = 500
            lista_filmes_imagem_erro.layoutParams.width = 500
            lista_filmes_textview_erro.text = "Não foi possível carregar lista"
            lista_filmes_imagem_erro.setBackgroundResource(R.drawable.torodo_chuva)
        }

        if (savedInstanceState == null) {
            viewModel.buscaTodos().observe(viewLifecycleOwner, Observer {
                adapter.atualiza(it)
                quandoBuscaTerminada(it!!)
            })
        }

        adapter.quandoItemClicado = quandoItemFragmentClicado
    }

}