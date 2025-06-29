package br.com.agatha.monfredini.studio_ghibli_api.repository

import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi.logErro
import br.com.agatha.monfredini.studio_ghibli_api.di.modules.BASE_URL
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import br.com.agatha.monfredini.studio_ghibli_api.model.Species
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.GhibliApiRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call

class CharactersRepository {

    fun getCharacterByMovie(
        viewModelScope: CoroutineScope,
        movie: Movie,
        whenFailConnection: () -> Unit,
        getCharacters: (character: List<GhibliCharacter>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            val characters = mutableListOf<GhibliCharacter>()
            val charactersIds = getCharactersIds(movie, whenFailConnection)

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

    private fun getCharactersIds(movie: Movie, whenFailConnection: () -> Unit): List<String> {
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
        whenFailConnection: () -> Unit
    ): List<String>? {
        val speciesId = id.replace("$BASE_URL/species/", "")
        val call = createSearchSpeciesById(speciesId)
        return try {
            val body = call.execute().body()
            body?.people
        } catch (excpetion: Exception) {
            logErro("Cannot Species People Ids", excpetion)
            whenFailConnection()
            null
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
        whenFailConnection: () -> Unit,
    ): GhibliCharacter? {
        val call = createSearchPeopleById(id)
        return try {
            val characterBody: GhibliCharacter? = call.execute().body()
            return characterBody
        } catch (e: Exception) {
            logErro("getCharacters: ${e.message}", e)
            whenFailConnection()
            null
        }
    }

    fun getGhibliCharacters(
        viewModelScope: CoroutineScope,
        whenFailConnection: () -> Unit,
        getGhibliPeople: (people: List<GhibliCharacter>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            val callPeople = createSearchGhibliPeople()
            val callSpecies = createSearchGhibliSpecies()
            try {
                val mutableListOf = mutableListOf<GhibliCharacter>()
                val people: List<GhibliCharacter>? = callPeople.execute().body()
                val species = callSpecies.execute().body()
                people?.let {
                    mutableListOf.addAll(people)
                }

                species?.forEach { specie ->
                    specie.people.forEach { id ->
                        val character = getPeopleById(id, whenFailConnection)
                        if (!mutableListOf.contains(character) && character != null) {
                            mutableListOf.add(character)
                        }
                    }
                }

                viewModelScope.launch {
                    getGhibliPeople(mutableListOf)
                }

            } catch (excpetion: Exception) {
                logErro("Cannot get Ghibli People", excpetion)
                whenFailConnection()
            }
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

    private fun createSearchGhibliPeople(): Call<List<GhibliCharacter>> {
        val retrofit = GhibliApiRetrofit()
        return retrofit.returnGhibliPeople()
    }

    private fun createSearchGhibliSpecies(): Call<List<Species>> {
        val retrofit = GhibliApiRetrofit()
        return retrofit.returnGhibliSpecies()
    }
}