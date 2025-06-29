package br.com.agatha.monfredini.studio_ghibli_api.repository

import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.NO_MOVIES_FOUND
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.GhibliApiRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call

class MovieListRepository {

    fun getMovies(viewModelScope: CoroutineScope, whenFailConnection: (message:String) -> Unit, getMovies: (List<Movie>) -> Unit) {
        val call = createService()

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                val response = call.execute()
                val movies: List<Movie>? = response.body()
                viewModelScope.launch {
                    movies?.let {
                        getMovies(it)
                    }
                }
            } catch (e: Exception) {
                LogsStudioGhibliApi.logErro("getMovies: ${e.message}", e)
                viewModelScope.launch {
                    whenFailConnection(NO_MOVIES_FOUND)
                }
            }
        }
    }

    private fun createService(): Call<List<Movie>> {
        val retrofit = GhibliApiRetrofit()
        return retrofit.returnMovies()
    }

}
