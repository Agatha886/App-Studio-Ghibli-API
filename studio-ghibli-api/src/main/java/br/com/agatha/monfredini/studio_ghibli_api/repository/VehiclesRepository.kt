package br.com.agatha.monfredini.studio_ghibli_api.repository

import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logErro
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.NO_VEHICLES_FOUND
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
                    viewModelScope.launch {
                        getVehicles(it)
                    }
                }
            } catch (exception: Exception) {
                logErro("Cannot Get Vehicles", exception)
                whenFailConnection(NO_VEHICLES_FOUND)
            }
        }

    }

    private fun createService(): Call<List<Vehicle>> {
        val retrofit = GhibliApiRetrofit()
        return retrofit.returnVehicles()
    }
}