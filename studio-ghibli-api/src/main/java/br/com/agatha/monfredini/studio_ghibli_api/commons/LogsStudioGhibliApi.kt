package br.com.agatha.monfredini.studio_ghibli_api.commons

import android.util.Log

object LogsStudioGhibliApi {
    private const val TAG_ERRO = "TAG_STUDIO_GHIBLI_API_ERRO"
    private const val TAG = "TAG_STUDIO_GHIBLI"

    fun logErro(message: String, error: Exception?) {
        Log.e(TAG_ERRO, message, error)
    }

    fun logInfo(message: String) {
        Log.i(TAG, message)
    }
}