package br.com.agatha.monfredini.studio_ghibli_api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.agatha.monfredini.studio_ghibli_api.LogsStudioGhibliApi
import br.com.agatha.monfredini.studio_ghibli_api.retrofit.service.MoviesRetrofit
import br.com.agatha.monfredini.studio_ghibli_api.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

internal class MovieListRepository {

    var whenFailConnection: () -> Unit = {}
    private val _liveData = MutableLiveData<List<Movie>?>()
    val liveData:LiveData<List<Movie>?> = _liveData

    fun getMovies() {
        val call = createService()

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                val response = call.execute()
                val movies: List<Movie>? = response.body()
                withContext(Dispatchers.Main) {
                    _liveData.value = movies
                }
            } catch (e: Exception) {
                LogsStudioGhibliApi.logErro("getMovies: ${e.message}", e)
                withContext(Dispatchers.Main){
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
