package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.repository.GhibliCharactersRepository

class GhibliCharactersViewModel(private val repository: GhibliCharactersRepository) : ViewModel() {

    private val _ghibliAllCharacters = MutableLiveData<List<GhibliCharacter>>()
    val ghibliAllCharacters: LiveData<List<GhibliCharacter>> = _ghibliAllCharacters

    fun getGhibliAllCharacters(whenFail: (mensage:String) -> Unit) {
        repository.getGhibliCharacters(viewModelScope, whenFail) { characters ->
            _ghibliAllCharacters.value = characters
            logInfo("Ghibli People = $characters")
        }
    }
}