package br.com.agatha.monfredini.studio_ghibli_api.retrofit.service

import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import br.com.agatha.monfredini.studio_ghibli_api.model.Species
import retrofit2.http.GET
import retrofit2.http.Path

interface GhibliApiService {

    @GET("films")
    fun getMovies(): retrofit2.Call<List<Movie>>

    @GET("people")
    fun searchGhibliPeople(): retrofit2.Call<List<GhibliCharacter>>

    @GET("people/{id}")
    fun searchCharacterById(@Path("id") id:String): retrofit2.Call<GhibliCharacter>

    @GET("species/{id}")
    fun searchSpeciesById(@Path("id") id:String): retrofit2.Call<Species>
}