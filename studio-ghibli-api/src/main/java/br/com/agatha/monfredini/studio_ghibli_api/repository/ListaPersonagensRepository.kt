package br.com.agatha.monfredini.studioghibli.repository

import android.util.Log
import br.com.agatha.monfredini.studioghibli.retrofit.service.FilmesRetrofit
import br.com.agatha.monfredini.studioghibli.ui.model.Personagem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

class ListaPersonagensRepository {

    var quandoConexaoFalha: () -> Unit = {}
    var quandoFinalizaBuscaPersonagem: (personagem: Personagem?) -> Unit = {}

    fun retornaPersonagem(id: String) {
        val call = criaCallBuscaPersonagem(id)
        var personagem: Personagem? = null

        CoroutineScope(IO).launch {
            try {
                val personagemBody = call.execute().body()
                withContext(Main) {
                    personagem = personagemBody
                    quandoFinalizaBuscaPersonagem(personagem)
                }

            } catch (e: Exception) {
                Log.e("EROO", "todos: ${e.message}")
                withContext(Main) {
                    quandoConexaoFalha()
                }
            }
        }
    }

    private fun criaCallBuscaPersonagem(id: String): Call<Personagem> {
        val retrofit = FilmesRetrofit()
        return retrofit.retornaPersonagens(id)
    }
}