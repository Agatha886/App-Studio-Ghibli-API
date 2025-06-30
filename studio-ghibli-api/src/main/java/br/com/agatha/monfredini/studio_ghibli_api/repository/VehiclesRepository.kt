package br.com.agatha.monfredini.studio_ghibli_api.repository

import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logErro
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.BASE_URL
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.NO_VEHICLES_FOUND
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.PARTIAL_CHARACTERS_LOADED
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.model.Vehicle
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.GhibliApiRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call

class VehiclesRepository {

    fun getVehicles(
        viewModelScope: CoroutineScope,
        whenFailConnection: (message: String) -> Unit,
        getVehicles: (vehicles: List<Vehicle>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            val call = createService()
            try {
                val vehicles = call.execute().body()
                vehicles?.let {
                    setPilot(vehicles, whenFailConnection)
                    viewModelScope.launch {
                        getVehicles(vehicles)
                    }
                }
            } catch (exception: Exception) {
                logErro("Cannot Get Vehicles", exception)
                whenFailConnection(NO_VEHICLES_FOUND)
            }
        }

    }

    private fun setPilot(
        vehicles: List<Vehicle>,
        whenFailConnection: (message: String) -> Unit
    ) {
        vehicles.forEach { vehicle ->
            val character = getPeopleById( vehicle.pilotId, whenFailConnection)
            character?.let {
                vehicle.pilotCharacter = it
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

    private fun createService(): Call<List<Vehicle>> {
        val retrofit = GhibliApiRetrofit()
        return retrofit.returnVehicles()
    }
}