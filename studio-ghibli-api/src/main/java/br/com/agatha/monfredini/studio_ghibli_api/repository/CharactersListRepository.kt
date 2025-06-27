package br.com.agatha.monfredini.studio_ghibli_api.repository

import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.MoviesRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

internal class CharactersListRepository {

    var whenFailConnection: () -> Unit = {}
    var getCharacterSucess: (character: GhibliCharacter?) -> Unit = {}

    fun getCharacter(id: String) {
        val call = createSearchByCharacter(id)
        var character: GhibliCharacter?

        CoroutineScope(IO).launch {
            try {
                val characterBody = call.execute().body()
                withContext(Main) {
                    character = characterBody
                    getCharacterSucess(character)
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