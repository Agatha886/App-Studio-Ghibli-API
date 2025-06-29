package br.com.agatha.monfredini.studio_ghibli_api.repository

import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logErro
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.NO_CHARACTERS_FOUND
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.PARTIAL_CHARACTERS_LOADED
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.model.Species
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.GhibliApiRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call

class CharactersRepository {

    fun getGhibliCharacters(
        viewModelScope: CoroutineScope,
        whenFailConnection: (mensage:String) -> Unit,
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
                logErro("Cannot get Ghibli All Chararcters", excpetion)
                whenFailConnection(NO_CHARACTERS_FOUND)
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
            logErro("getPeopleById: ${e.message}", e)
            whenFailConnection(PARTIAL_CHARACTERS_LOADED)
            null
        }
    }

    private fun createSearchPeopleById(id: String): Call<GhibliCharacter> {
        val retrofit = GhibliApiRetrofit()
        return retrofit.returnCharacterById(id)
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