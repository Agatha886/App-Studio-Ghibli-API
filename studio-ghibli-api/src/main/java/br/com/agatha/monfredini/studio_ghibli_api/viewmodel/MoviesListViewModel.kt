package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.agatha.monfredini.studio_ghibli_api.repository.MovieListRepository
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie

internal class MoviesListViewModel(private val repository: MovieListRepository) : ViewModel() {
    private var whenFail: () -> Unit = {}

    fun getMovies(): LiveData<List<Movie>?> {
       repository.getMovies()
       repository.whenFailConnection = whenFail
       return repository.liveData
    }

}