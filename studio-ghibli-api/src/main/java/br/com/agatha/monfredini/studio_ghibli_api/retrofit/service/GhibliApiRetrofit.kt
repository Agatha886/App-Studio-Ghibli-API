package br.com.agatha.monfredini.studio_ghibli_api.retrofit.service

import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.BASE_URL
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.model.Location
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import br.com.agatha.monfredini.studio_ghibli_api.model.Species
import br.com.agatha.monfredini.studio_ghibli_api.model.Vehicle
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GhibliApiRetrofit {

    private val retrofit: Retrofit = baseRetrofit()
    private val service = retrofit.create(GhibliApiService::class.java)

    private fun baseRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun returnMovies(): Call<List<Movie>> {
        return service.getMovies()
    }

    fun returnCharacterById(id: String): Call<GhibliCharacter> {
        return service.searchCharacterById(id)
    }

    fun returnSpeciesById(id: String): Call<Species> {
        return service.searchSpeciesById(id)
    }

    fun returnGhibliPeople(): Call<List<GhibliCharacter>> {
        return service.searchGhibliPeople()
    }

    fun returnGhibliSpecies(): Call<List<Species>> {
        return service.searchGhibliSpecies()
    }

    fun returnVehicles(): Call<List<Vehicle>> {
        return service.searchGhibliVehicles()
    }

    fun returnLocations(): Call<List<Location>> {
        return service.searchGhibliLocations()
    }

}