package br.com.agatha.monfredini.studioghibli.ui.activity

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.agatha.monfredini.studioghibli.R
import br.com.agatha.monfredini.studioghibli.ui.CHAVE_FILME
import br.com.agatha.monfredini.studioghibli.ui.CHAVE_PERSONAGEM
import br.com.agatha.monfredini.studioghibli.ui.TAG_DETALHES
import br.com.agatha.monfredini.studioghibli.ui.TAG_LISTA_FILMES
import br.com.agatha.monfredini.studioghibli.ui.fragment.DetalhesFilmesFragment
import br.com.agatha.monfredini.studioghibli.ui.fragment.DetalhesPersonagemFragment
import br.com.agatha.monfredini.studioghibli.ui.fragment.ListaFilmesFragment
import br.com.agatha.monfredini.studioghibli.ui.fragment.quandoTelaFinalizada
import br.com.agatha.monfredini.studioghibli.ui.model.Filme
import br.com.agatha.monfredini.studioghibli.ui.model.Personagem
import br.com.agatha.monfredini.studioghibli.viewmodel.ListaPersonagensViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

//    private val viewModelPersonagens: ListaPersonagensViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val listaFilmesfragment = ListaFilmesFragment()
            transacaoFragment {
                replace(R.id.activity_main_container, listaFilmesfragment, TAG_LISTA_FILMES)
            }
        }

    }

    override fun onAttachFragment(fragment: androidx.fragment.app.Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is ListaFilmesFragment -> {
                configuraLista(fragment)
            }
            is DetalhesFilmesFragment -> {
                configuraDetalhesFilmes(fragment)
            }
        }
    }

    private fun configuraLista(fragment: ListaFilmesFragment) {
        fragment.quandoItemFragmentClicado = {
            vaiParaDetalhes(it)
//            it.listaPersonagens = carregaPersonagens(it)
        }
    }

    //    fun carregaPersonagens(filme: Filme): MutableList<Personagem> {
//        val filmes: MutableList<Personagem> = mutableListOf()
//        viewModelPersonagens.buscaPersonagensPorFilme(filme)
//        viewModelPersonagens.listaPersonagens.observe(this@MainActivity, Observer {
//            filmes.addAll(it)
//        })
//        return filmes
//    }


    private fun vaiParaDetalhes(filme: Filme) {
        val detalhesFilmesfragment = DetalhesFilmesFragment()
        val dados = Bundle()
        dados.putSerializable(CHAVE_FILME, filme)
        detalhesFilmesfragment.arguments = dados

        transacaoFragment {
            replace(R.id.activity_main_container, detalhesFilmesfragment, TAG_DETALHES)
            addToBackStack(TAG_LISTA_FILMES)
        }

    }

    private fun configuraDetalhesFilmes(fragment: DetalhesFilmesFragment) {
        fragment.quandoTelaFinalizada = {
            fechaFragmentDetalhes()
        }

        fragment.quandoPersonagemClicado = {
            vaiParaDetalhesPersonagem(it)
        }
    }

    private fun vaiParaDetalhesPersonagem(personagem: Personagem) {
        val detalhesPersonagemFragment = DetalhesPersonagemFragment()
        val dados = Bundle()
        dados.putSerializable(CHAVE_PERSONAGEM, personagem)
        detalhesPersonagemFragment.arguments = dados

        transacaoFragment {
            replace(R.id.activity_main_container, detalhesPersonagemFragment)
            addToBackStack(TAG_DETALHES)
        }
    }

    private fun fechaFragmentDetalhes() {
        supportFragmentManager
            .findFragmentByTag(TAG_DETALHES)?.let { fragment ->
                transacaoFragment {
                    remove(fragment)
                }
                supportFragmentManager.popBackStack()
            }
    }

}
