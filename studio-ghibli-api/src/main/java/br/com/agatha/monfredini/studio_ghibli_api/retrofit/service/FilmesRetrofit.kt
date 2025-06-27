package br.com.agatha.monfredini.studio_ghibli_api.retrofit.service

import br.com.agatha.monfredini.studio_ghibli_api.ui.model.Filme
import br.com.agatha.monfredini.studio_ghibli_api.ui.model.Personagem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmesRetrofit {

    val retrofit: Retrofit = retrofitBase()
    val service = retrofit.create(FilmeService::class.java)

    fun retornaFilme(): Call<List<Filme>> {
        return service.buscaTodos()
    }

    private fun retrofitBase() = Retrofit.Builder()
        .baseUrl("https://ghibliapi.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun retornaPersonagens(id: String): Call<Personagem> {
        return service.buscaPersonagemPorId(id)
    }


}