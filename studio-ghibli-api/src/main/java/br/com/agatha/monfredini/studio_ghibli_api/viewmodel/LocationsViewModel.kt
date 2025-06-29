package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.Location
import br.com.agatha.monfredini.studio_ghibli_api.repository.LocationsRepository

class LocationsViewModel(private val repository: LocationsRepository) : ViewModel() {
    private val _locationsLiveDate = MutableLiveData<List<Location>>()
    val locationLiveData: LiveData<List<Location>> = _locationsLiveDate

    fun getLocations(whenFailConnection: (message: String) -> Unit) {
        repository.getLocations(viewModelScope, whenFailConnection) { locations ->
            _locationsLiveDate.value = locations
            logInfo("Locations = $locations")
        }
    }
}