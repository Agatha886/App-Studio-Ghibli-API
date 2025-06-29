package br.com.agatha.monfredini.studio_ghibli_api.repository

import androidx.lifecycle.MutableLiveData
import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi
import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.di.modules.BASE_URL
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.MoviesRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

class CharactersListRepository {

    var whenFailConnection: () -> Unit = {}
    private val teste = mutableListOf<GhibliCharacter>()

    fun getCharacterByMovie(
        peopleList: List<String>,
        getCharacter: (character: MutableList<GhibliCharacter>) -> Unit
    ) {
        for (pessoaUrl in peopleList) {
            val pessoaId = pessoaUrl.replace("$BASE_URL/people/", "")
            getCharacterById(pessoaId, getCharacter)
        }
    }

    private fun getCharacterById(
        id: String,
        getCharacter: (characters: MutableList<GhibliCharacter>) -> Unit
    ) {
        val call = createSearchByCharacter(id)
        CoroutineScope(IO).launch {
            try {
                val characterBody = call.execute().body()
                logInfo("personagem : $characterBody")
                CoroutineScope(Main).launch {
                    characterBody?.let {
                        teste.add(it)
                        logInfo("teste : $teste")
                        getCharacter(teste)
                    }
                }

            } catch (e: Exception) {
                LogsStudioGhibliApi.logErro("getCharacters: ${e.message}", e)
                withContext(Main) {
                    whenFailConnection()
                }
            }
        }
    }

    private fun createSearchByCharacter(id: String): Call<GhibliCharacter> {
        val retrofit = MoviesRetrofit()
        return retrofit.returnCharacters(id)
    }
}