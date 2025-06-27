package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.agatha.monfredini.studio_ghibli_api.repository.ListaFilmesRepository
import br.com.agatha.monfredini.studio_ghibli_api.ui.model.Filme

class ListaFilmesViewModel(private val repository: ListaFilmesRepository) : ViewModel() {
    var quandoFalha: () -> Unit = {}

    fun buscaTodos(): LiveData<List<Filme>?> {
       repository.todos()
       repository.quandoConexaoFalha = quandoFalha
       return repository.liveData
    }

}