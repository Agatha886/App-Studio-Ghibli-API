package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.Vehicle
import br.com.agatha.monfredini.studio_ghibli_api.repository.VehiclesRepository

class VehiclesViewModel(private val repository: VehiclesRepository) : ViewModel() {

    private val _vehiclesLiveData = MutableLiveData<List<Vehicle>>()
    val vehiclesLiveData: LiveData<List<Vehicle>> = _vehiclesLiveData

    fun getVehicles(whenFailConnection: (message: String) -> Unit) {
        repository.getVehicles(viewModelScope, whenFailConnection) { vehicles->
            _vehiclesLiveData.value = vehicles
            logInfo("Vehicles : $vehicles")
        }
    }
}