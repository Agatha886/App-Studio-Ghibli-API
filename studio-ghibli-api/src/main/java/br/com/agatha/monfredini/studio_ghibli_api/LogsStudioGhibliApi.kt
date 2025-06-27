package br.com.agatha.monfredini.studio_ghibli_api

import android.util.Log

internal object LogsStudioGhibliApi {
    private const val TAG_ERRO = "STUDIO_GHIBLI_API_ERRO"

    fun logErro(message:String, error: Exception) {
        Log.e(TAG_ERRO, message, error)
    }
}