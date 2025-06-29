package br.com.agatha.monfredini.studio_ghibli_api.repository

import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi.logErro
import br.com.agatha.monfredini.studio_ghibli_api.di.modules.BASE_URL
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.MoviesRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call

class CharactersListRepository {

    fun getCharacterByMovie(
        viewModelScope: CoroutineScope,
        charactersIds: List<String>,
        whenFailConnection: () -> Unit,
        getCharacters: (character: List<GhibliCharacter>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            val characters = mutableListOf<GhibliCharacter>()

            filterCharacters(charactersIds, whenFailConnection, characters)

            viewModelScope.launch {
                if (characters.isEmpty()) {
                    whenFailConnection()
                    logErro("Characters List is empty. This Movie Doesn't have people", null)
                } else {
                    getCharacters(characters)
                }
            }
        }
    }

    private fun filterCharacters(
        charactersIds: List<String>,
        whenFailConnection: () -> Unit,
        characters: MutableList<GhibliCharacter>
    ) {
        for ((index, id) in charactersIds.withIndex()) {
            val characterId = id.replace("$BASE_URL/people/", "")

            if (characterId.isNotBlank()) {
                val character = getCharacterById(characterId, whenFailConnection)

                character?.let {
                    characters.add(character)
                }
            }

            if (index == charactersIds.lastIndex) {
                break
            }
        }
    }

    private fun getCharacterById(
        id: String,
        whenFailConnection: () -> Unit,
    ): GhibliCharacter? {
        val call = createSearchByCharacter(id)
        return try {
            val characterBody = call.execute().body()
            return characterBody
        } catch (e: Exception) {
            logErro("getCharacters: ${e.message}", e)
            whenFailConnection()
            null
        }
    }

    private fun createSearchByCharacter(id: String): Call<GhibliCharacter> {
        val retrofit = MoviesRetrofit()
        return retrofit.returnCharacters(id)
    }
}