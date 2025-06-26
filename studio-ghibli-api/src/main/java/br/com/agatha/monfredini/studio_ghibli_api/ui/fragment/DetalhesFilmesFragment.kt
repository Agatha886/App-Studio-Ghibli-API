package br.com.agatha.monfredini.studioghibli.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import br.com.agatha.monfredini.studioghibli.R
import br.com.agatha.monfredini.studioghibli.ui.CHAVE_FILME
import br.com.agatha.monfredini.studioghibli.ui.MENSAGEM_FILME_INVALIDO
import br.com.agatha.monfredini.studioghibli.ui.model.Filme
import br.com.agatha.monfredini.studioghibli.ui.model.Personagem
import br.com.agatha.monfredini.studioghibli.ui.recycler.adapter.ListaPersonagensAdapter
import br.com.agatha.monfredini.studioghibli.viewmodel.ListaPersonagensViewModel
import kotlinx.android.synthetic.main.detalhes_filmes_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class DetalhesFilmesFragment : Fragment() {

    private val viewModel by viewModel<ListaPersonagensViewModel>()

    private val filme: Serializable by lazy {
        arguments?.getSerializable(CHAVE_FILME) ?: throw IllegalArgumentException(
            MENSAGEM_FILME_INVALIDO
        )
    }

    var quandoPersonagemClicado:(personagem: Personagem) -> Unit ={_:Personagem ->}

    private val filmeEscolhido: Filme by lazy {
        filme as Filme
    }

    private val adapter: ListaPersonagensAdapter by lazy {
        context?.let {
            ListaPersonagensAdapter(it)
        } ?: throw IllegalArgumentException("Contexto inválido")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detalhes_filmes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            verificaFilme()
        } catch (e: Exception) {
            Log.e("ERRO", "onViewCreated: ${e.message}")
            fechaFragement()
        }
        adapter.quandoItemClicado ={
            quandoPersonagemClicado(it)
        }
    }

    private fun verificaFilme() {
        if (filmeEscolhido.title != null) {
            preencheCampos(filmeEscolhido)
        } else {
            fechaFragement()
        }
    }

    private fun atualizaAdapter() {
        detalhes_filmes_recycler.adapter = adapter
        viewModel.buscaPersonagensPorFilme(filmeEscolhido)
        viewModel.listaPersonagens.observe(viewLifecycleOwner, Observer {
            adapter.atualiza(it)
        })

    }

    private fun preencheCampos(filmeEscolhido: Filme) {
        activity?.title = filmeEscolhido.title
        detalhes_filmes_titulo.setText(filmeEscolhido.title)
        detalhes_filmes_ano.setText(filmeEscolhido.release_date)
        detalhes_filme_sinopse.setText(filmeEscolhido.description)
        detalhes_filme_imagem.setImageResource(filmeEscolhido.poster)
        atualizaAdapter()
    }

}

