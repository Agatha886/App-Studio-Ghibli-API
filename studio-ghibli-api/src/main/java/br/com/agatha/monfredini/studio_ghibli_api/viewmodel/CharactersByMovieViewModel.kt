package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersByMovieRepository
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie

class CharactersByMovieViewModel(private val repository: CharactersByMovieRepository) : ViewModel() {

    private val _characterList = MutableLiveData<List<GhibliCharacter>>()
    val characterList: LiveData<List<GhibliCharacter>> = _characterList

    fun getCharacterByMovie(movie: Movie, whenFail: (message:String) -> Unit) {
        repository.getCharacterByMovie(viewModelScope, movie, whenFail) { characters ->
            _characterList.value = characters
            logInfo("Characters = $characters")
        }
    }
}