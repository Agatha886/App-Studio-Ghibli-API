package br.com.agatha.monfredini.studio_ghibli_api.repository

import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logErro
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.BASE_URL
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.NO_CHARACTERS_FOUND
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.PARTIAL_CHARACTERS_LOADED
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import br.com.agatha.monfredini.studio_ghibli_api.model.Species
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.GhibliApiRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call

class CharactersByMovieRepository {

    fun getCharacterByMovie(
        viewModelScope: CoroutineScope,
        movie: Movie,
        whenFailConnection: (mensage:String) -> Unit,
        getCharacters: (character: List<GhibliCharacter>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            val characters = mutableListOf<GhibliCharacter>()
            val charactersIds = getCharactersIds(movie, whenFailConnection)

            filterCharacters(charactersIds, whenFailConnection, characters)

            viewModelScope.launch {
                if (characters.isEmpty()) {
                    whenFailConnection(NO_CHARACTERS_FOUND)
                    logErro("Characters List is empty. This Movie Doesn't have people", null)
                } else {
                    getCharacters(characters)
                }
            }
        }
    }

    private fun getCharactersIds(movie: Movie, whenFailConnection: (mensage:String) -> Unit): List<String> {
        val mutableListOf = mutableListOf<String>()
        mutableListOf.addAll(movie.people)
        movie.species.forEach { id ->
            getGhibliSpeciesIds(id, whenFailConnection)?.forEach { idSpecies ->
                if (idSpecies !in mutableListOf) {
                    mutableListOf.add(idSpecies)
                }
            }
        }

        return mutableListOf
    }

    private fun getGhibliSpeciesIds(
        id: String,
        whenFailConnection: (mensage:String) -> Unit
    ): List<String>? {
        val speciesId = id.replace("$BASE_URL/species/", "")
        val call = createSearchSpeciesById(speciesId)
        return try {
            val body = call.execute().body()
            body?.people
        } catch (excpetion: Exception) {
            logErro("Cannot Species People Ids", excpetion)
            whenFailConnection(PARTIAL_CHARACTERS_LOADED)
            null
        }
    }

    private fun filterCharacters(
        charactersIds: List<String>,
        whenFailConnection: (mensage:String) -> Unit,
        characters: MutableList<GhibliCharacter>
    ) {
        for ((index, id) in charactersIds.withIndex()) {
            val characterId = id.replace("$BASE_URL/people/", "")

            if (characterId.isNotBlank()) {
                val character = getPeopleById(characterId, whenFailConnection)

                character?.let {
                    characters.add(character)
                }
            }

            if (index == charactersIds.lastIndex) {
                break
            }
        }
    }

    private fun getPeopleById(
        id: String,
        whenFailConnection: (mensage:String) -> Unit,
    ): GhibliCharacter? {
        val call = createSearchPeopleById(id)
        return try {
            val characterBody: GhibliCharacter? = call.execute().body()
            return characterBody
        } catch (e: Exception) {
            logErro("getCharacters: ${e.message}", e)
            whenFailConnection(PARTIAL_CHARACTERS_LOADED)
            null
        }
    }

    private fun createSearchPeopleById(id: String): Call<GhibliCharacter> {
        val retrofit = GhibliApiRetrofit()
        return retrofit.returnCharacterById(id)
    }

    private fun createSearchSpeciesById(id: String): Call<Species> {
        val retrofit = GhibliApiRetrofit()
        return retrofit.returnSpeciesById(id)
    }
}