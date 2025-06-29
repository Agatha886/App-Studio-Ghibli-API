package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import br.com.agatha.monfredini.studio_ghibli_api.repository.MoviesRepository

class MoviesViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val _liveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _liveData

    fun getMovies(whenFail: (message: String) -> Unit) {
        repository.getMovies(viewModelScope, whenFail) { movies ->
            _liveData.value = movies
        }
    }

}