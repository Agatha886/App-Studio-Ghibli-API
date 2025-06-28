package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import br.com.agatha.monfredini.studio_ghibli_api.repository.MovieListRepository

class MoviesListViewModel(private val repository: MovieListRepository) : ViewModel() {
    var whenFail: () -> Unit = {}
    private val _liveData = MutableLiveData<List<Movie>?>()
    val movies: LiveData<List<Movie>?> = _liveData

    fun getMovies() {
        repository.whenFailConnection = whenFail
        repository.getMovies(_liveData)
    }

}