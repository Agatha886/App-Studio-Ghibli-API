package br.com.agatha.monfredini.studio_ghibli_api.repository

import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logErro
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.BASE_URL
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.NO_LOCATIONS_FOUND
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.NO_RESIDENTS_FOUND
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.PARTIAL_CHARACTERS_LOADED
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.model.Location
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.GhibliApiRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call

class LocationsRepository {

    fun getLocations(
        viewModelScope: CoroutineScope,
        whenFailConnection: (message: String) -> Unit,
        getLocations: (List<Location>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            val call = createService()
            try {
                val locations = call.execute().body()
                locations?.let {
                    viewModelScope.launch {
                        getLocations(it)
                    }
                }
            } catch (exception: Exception) {
                whenFailConnection(NO_LOCATIONS_FOUND)
                logErro("Cannot getLocations", exception)
            }
        }

    }

    fun getResidents(
        viewModelScope: CoroutineScope,
        location: Location,
        whenFailConnection: (mensage: String) -> Unit,
        setResidents: (list: List<GhibliCharacter?>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            val characeters = mutableListOf<GhibliCharacter?>()
            location.residentsUrl.forEach { url ->
                val id = url.replace("$BASE_URL/people/", "")
                val character: GhibliCharacter? = getPeopleById(id, whenFailConnection)
                characeters.add(character)
            }
            viewModelScope.launch {
                if (characeters.isEmpty()) {
                    whenFailConnection(NO_RESIDENTS_FOUND)
                } else {
                    setResidents(characeters)
                }
            }
        }
    }

    private fun getPeopleById(
        id: String,
        whenFailConnection: (mensage: String) -> Unit,
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

    private fun createService(): Call<List<Location>> {
        val retrofit = GhibliApiRetrofit()
        return retrofit.returnLocations()
    }

}