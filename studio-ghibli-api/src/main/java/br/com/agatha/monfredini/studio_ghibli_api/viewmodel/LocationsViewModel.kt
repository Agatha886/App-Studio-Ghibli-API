package br.com.agatha.monfredini.studio_ghibli_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.model.Location
import br.com.agatha.monfredini.studio_ghibli_api.repository.LocationsRepository

class LocationsViewModel(private val repository: LocationsRepository) : ViewModel() {

    private val _locationsLiveDate = MutableLiveData<List<Location>>()
    val locationLiveData: LiveData<List<Location>> = _locationsLiveDate

    private val _residentsLiveDate = MutableLiveData<List<GhibliCharacter?>>()
    val residentsLiveData: LiveData<List<GhibliCharacter?>> = _residentsLiveDate

    fun getLocations(whenFailConnection: (message: String) -> Unit) {
        repository.getLocations(viewModelScope, whenFailConnection) { locations ->
            _locationsLiveDate.value = locations
            logInfo("Locations = $locations")
        }
    }

    fun getResidents(location: Location, whenFailConnection: (message: String) -> Unit) {
        repository.getResidents(viewModelScope, location, whenFailConnection) { list ->
            _residentsLiveDate.value = list
            logInfo("${location.imageName}'s Residents = $list")
        }
    }
}