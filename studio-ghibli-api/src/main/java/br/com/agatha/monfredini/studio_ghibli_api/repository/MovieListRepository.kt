package br.com.agatha.monfredini.studio_ghibli_api.repository

import androidx.lifecycle.MutableLiveData
import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.MoviesRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

class MovieListRepository {

    var whenFailConnection: () -> Unit = {}

    fun getMovies(liveData: MutableLiveData<List<Movie>?>) {
        val call = createService()

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                val response = call.execute()
                val movies: List<Movie>? = response.body()
                withContext(Dispatchers.Main) {
                    liveData.value = movies
                }
            } catch (e: Exception) {
                LogsStudioGhibliApi.logErro("getMovies: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    whenFailConnection()
                }
            }
        }
    }

    private fun createService(): Call<List<Movie>> {
        val retrofit = MoviesRetrofit()
        return retrofit.returnMovies()
    }

}
