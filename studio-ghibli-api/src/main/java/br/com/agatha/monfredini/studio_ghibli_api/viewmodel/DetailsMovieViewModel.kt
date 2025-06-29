package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersRepository
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import kotlinx.coroutines.launch

class DetailsMovieViewModel(private val repository: CharactersRepository) : ViewModel() {

    private val _characterList = MutableLiveData<List<GhibliCharacter>>()
    val characterList: LiveData<List<GhibliCharacter>> = _characterList

    private val _ghibliPeople = MutableLiveData<List<GhibliCharacter>>()
    val ghibliPeople: LiveData<List<GhibliCharacter>> = _ghibliPeople

    fun getCharacterByMovie(movie: Movie, whenFail: () -> Unit) {
        repository.getCharacterByMovie(viewModelScope, movie, whenFail) { characters ->
            _characterList.value = characters
            logInfo("Characters = $characters")
        }
    }

    fun getGhibliAllCharacters(whenFail: () -> Unit) {
        repository.getGhibliCharacters(viewModelScope, whenFail) { characters ->
            _ghibliPeople.value = characters
            logInfo("Ghibli People = $characters")
        }
    }
}