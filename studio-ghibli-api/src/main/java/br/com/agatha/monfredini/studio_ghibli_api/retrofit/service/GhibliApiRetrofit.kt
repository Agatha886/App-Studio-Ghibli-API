package br.com.agatha.monfredini.studio_ghibli_api.retrofit.service

import br.com.agatha.monfredini.studio_ghibli_api.di.modules.BASE_URL
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import br.com.agatha.monfredini.studio_ghibli_api.model.Species
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GhibliApiRetrofit {

    private val retrofit: Retrofit = baseRetrofit()
    private val service = retrofit.create(GhibliApiService::class.java)

    fun returnMovies(): Call<List<Movie>> {
        return service.getMovies()
    }

    private fun baseRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun returnCharacterById(id: String): Call<GhibliCharacter> {
        return service.searchCharacterById(id)
    }

    fun returnSpeciesById(id: String): Call<Species> {
        return service.searchSpeciesById(id)
    }

    fun returnGhibliPeople(): Call<List<GhibliCharacter>> {
        return service.searchGhibliPeople()
    }
    
}