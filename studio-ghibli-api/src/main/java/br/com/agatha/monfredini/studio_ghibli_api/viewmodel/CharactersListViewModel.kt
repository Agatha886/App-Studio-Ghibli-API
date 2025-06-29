package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.di.modules.BASE_URL
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersListRepository
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie

class CharactersListViewModel(private val repository: CharactersListRepository) : ViewModel() {

    var whenFail: () -> Unit = {}
    private val _characterList = MutableLiveData<MutableList<GhibliCharacter>>()
    val characterList: LiveData<MutableList<GhibliCharacter>> = _characterList

    fun getCharacterByMovie(movie: Movie) {
        val pessoasUrl: List<String> = movie.people
        repository.whenFailConnection = whenFail
        repository.getCharacterByMovie(pessoasUrl) { characters ->
            _characterList.value = characters
        }
        logInfo("_characterList = ${_characterList.value}")
    }
}