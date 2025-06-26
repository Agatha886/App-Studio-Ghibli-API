package br.com.agatha.monfredini.studioghibli.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.agatha.monfredini.studioghibli.repository.ListaPersonagensRepository
import br.com.agatha.monfredini.studioghibli.ui.model.Filme
import br.com.agatha.monfredini.studioghibli.ui.model.Personagem

class ListaPersonagensViewModel(private val repository: ListaPersonagensRepository): ViewModel() {

    var quandoFalha: () -> Unit = {}
    val listaPersonagens = MutableLiveData<List<Personagem>>()
    val personagem = MutableLiveData<Personagem>()

    private fun buscapersonagemPorId(id: String) {
        repository.quandoConexaoFalha = quandoFalha
        repository.retornaPersonagem(id)
    }

    fun buscaPersonagensPorFilme(filme: Filme) {
        val pessoasUrl = filme.people
        val personagens = ArrayList<Personagem>()

        for (pessoaUrl in pessoasUrl) {
            val pessoaId = pessoaUrl.replace("https://ghibliapi.herokuapp.com/people/", "")
            buscapersonagemPorId(pessoaId)
            repository.quandoFinalizaBuscaPersonagem = {
                if (it != null) {
                    val personagemBuscado = it
                    personagens.add(personagemBuscado)
                    listaPersonagens.value = personagens
                    personagem.value = it
                }
            }
        }
//        val listaDePersonagens: List<Personagem> = personagens
//        Log.i("FORA DO FOR", "FORA: ${listaDePersonagens}")
//        listaPersonagens.value = listaDePersonagens
    }
}