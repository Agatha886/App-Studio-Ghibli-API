package br.com.agatha.monfredini.studio_ghibli_api.retrofit.service

import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("films")
    fun getMovies(): retrofit2.Call<List<Movie>>

    @GET("people/{id}")
    fun searchCharacterById(@Path("id") id:String): retrofit2.Call<GhibliCharacter>
}