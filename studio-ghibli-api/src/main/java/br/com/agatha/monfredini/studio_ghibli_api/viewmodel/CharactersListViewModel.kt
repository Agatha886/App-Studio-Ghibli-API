package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersListRepository
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie

class CharactersListViewModel(private val repository: CharactersListRepository) : ViewModel() {

    private val _characterList = MutableLiveData<List<GhibliCharacter>>()
    val characterList: LiveData<List<GhibliCharacter>> = _characterList

    fun getCharacterByMovie(movie: Movie, whenFail: () -> Unit = {}) {
        val charactersIds: List<String> = movie.people
        repository.getCharacterByMovie(viewModelScope, charactersIds, whenFail) { characters ->
            _characterList.value = characters
            logInfo("Characters = $characters")
        }
    }
}