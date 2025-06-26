package br.com.agatha.monfredini.studioghibli.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.agatha.monfredini.studioghibli.retrofit.service.FilmesRetrofit
import br.com.agatha.monfredini.studioghibli.ui.model.Filme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

class ListaFilmesRepository {

    var quandoConexaoFalha: () -> Unit = {}
    val liveData = MutableLiveData<List<Filme>?>()

    fun todos() {
        val call = criaService()

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                val resposta = call.execute()
                val filmes: List<Filme>? = resposta.body()
                withContext(Dispatchers.Main) {
                    liveData.value = filmes
                }
            } catch (e: Exception) {
                Log.e("EROO", "todos: ${e.message}")
                withContext(Dispatchers.Main){
                    quandoConexaoFalha()
                }
            }
        }
    }

    private fun criaService(): Call<List<Filme>> {
        val retrofit = FilmesRetrofit()
        return retrofit.retornaFilme()
    }

}
