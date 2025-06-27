package br.com.agatha.monfredini.studio_ghibli_api.retrofit.service

import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRetrofit {

    private val retrofit: Retrofit = baseRetrofit()
    private val service = retrofit.create(MovieService::class.java)

    fun returnMovies(): Call<List<Movie>> {
        return service.getMovies()
    }

    private fun baseRetrofit() = Retrofit.Builder()
        .baseUrl("https://ghibliapi.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun returnCharacters(id: String): Call<GhibliCharacter> {
        return service.searchCharacterById(id)
    }
    
}