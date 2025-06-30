package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersRepository

class CharactersViewModel(private val repository: CharactersRepository) : ViewModel() {

    private val _allCharacters = MutableLiveData<List<GhibliCharacter>>()
    val allCharacters: LiveData<List<GhibliCharacter>> = _allCharacters

    fun getAllCharacters(whenFailConnection: (mensage:String) -> Unit) {
        repository.getAllCharacters(viewModelScope, whenFailConnection) { characters ->
            _allCharacters.value = characters
            logInfo("Ghibli All Characters = $characters")
        }
    }
}