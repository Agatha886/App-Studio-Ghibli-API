package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersListRepository
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie

internal class CharactersListViewModel(private val repository: CharactersListRepository): ViewModel() {

    private var whenFail: () -> Unit = {}
    private val characterList = MutableLiveData<List<GhibliCharacter>>()
    private val character = MutableLiveData<GhibliCharacter>()

    private fun getCharacterById(id: String) {
        repository.whenFailConnection = whenFail
        repository.getCharacter(id)
    }

    fun getCharacterByMovie(movie: Movie) {
        val pessoasUrl = movie.people
        val characters = ArrayList<GhibliCharacter>()

        for (pessoaUrl in pessoasUrl) {
            val pessoaId = pessoaUrl.replace("https://ghibliapi.herokuapp.com/people/", "")
            getCharacterById(pessoaId)
            repository.getCharacterSucess = {
                if (it != null) {
                    val foundCharacter = it
                    characters.add(foundCharacter)
                    characterList.value = characters
                    character.value = it
                }
            }
        }
    }
}