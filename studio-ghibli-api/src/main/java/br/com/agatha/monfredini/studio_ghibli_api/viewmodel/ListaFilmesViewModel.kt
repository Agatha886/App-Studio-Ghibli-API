package br.com.agatha.monfredini.studioghibli.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.agatha.monfredini.studioghibli.repository.ListaFilmesRepository
import br.com.agatha.monfredini.studioghibli.ui.model.Filme

class ListaFilmesViewModel(private val repository: ListaFilmesRepository) : ViewModel() {
    var quandoFalha: () -> Unit = {}

    fun buscaTodos(): LiveData<List<Filme>?> {
       repository.todos()
       repository.quandoConexaoFalha = quandoFalha
       return repository.liveData
    }

}