package br.com.agatha.monfredini.studio_ghibli_api.retrofit.service

import br.com.agatha.monfredini.studio_ghibli_api.ui.model.Filme
import br.com.agatha.monfredini.studio_ghibli_api.ui.model.Personagem
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmeService {

    @GET("films")
    fun buscaTodos(): retrofit2.Call<List<Filme>>

    @GET("people/{id}")
    fun buscaPersonagemPorId(@Path("id") id:String): retrofit2.Call<Personagem>


}