package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.agatha.monfredini.studio_ghibli_api.repository.ListaPersonagensRepository
import br.com.agatha.monfredini.studio_ghibli_api.ui.model.Filme
import br.com.agatha.monfredini.studio_ghibli_api.ui.model.Personagem

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
    }
}