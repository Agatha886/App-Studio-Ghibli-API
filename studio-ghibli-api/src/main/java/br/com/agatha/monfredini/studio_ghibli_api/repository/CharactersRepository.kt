package br.com.agatha.monfredini.studio_ghibli_api.repository

import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi.logErro
import br.com.agatha.monfredini.studio_ghibli_api.di.modules.BASE_URL
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.GhibliApiRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call

class CharactersRepository {

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

    fun getCharacterById(
        id: String,
        whenFailConnection: () -> Unit,
    ): GhibliCharacter? {
        val call = createSearchCharacterById(id)
        return try {
            val characterBody: GhibliCharacter? = call.execute().body()
            return characterBody
        } catch (e: Exception) {
            logErro("getCharacters: ${e.message}", e)
            whenFailConnection()
            null
        }
    }

    fun getGhibliPeople(
        viewModelScope: CoroutineScope,
        whenFailConnection: () -> Unit,
        getGhibliPeople: (people: List<GhibliCharacter>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            val call = createSearchGhibliPeople()
            try {
                val people: List<GhibliCharacter>? = call.execute().body()
                people?.let {
                    viewModelScope.launch {
                        getGhibliPeople(it)
                    }
                }
            } catch (excpetion: Exception) {
                logErro("Cannot get Ghibli People", excpetion)
                whenFailConnection()
            }
        }
    }

    private fun createSearchCharacterById(id: String): Call<GhibliCharacter> {
        val retrofit = GhibliApiRetrofit()
        return retrofit.returnCharacterById(id)
    }

    private fun createSearchGhibliPeople(): Call<List<GhibliCharacter>> {
        val retrofit = GhibliApiRetrofit()
        return retrofit.returnGhibliPeople()
    }
}