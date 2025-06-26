package br.com.agatha.monfredini.studioghibli.ui.recycler.adapter

import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView



fun RecyclerView.ViewHolder.setImagem(string: String, campoImagem: ImageView, contex: Context,
                                      quandoResorceNaoEncontrado: () -> Unit, quandoResorceEncontrado: (resorce: Int) -> Unit ) {
    val resorce = buscaImagem(string, campoImagem, contex)
    if (resorce > 0) {
        campoImagem.setBackgroundResource(resorce)
        quandoResorceEncontrado(resorce)
    } else {
        quandoResorceNaoEncontrado()
    }
}


private fun buscaImagem(string: String, campoImagem: ImageView, contex: Context): Int {
    val poster = getThumbnailName(string)
    val resorce = campoImagem.resources.getIdentifier(
        poster, "drawable",
        contex.getPackageName()
    )
    return resorce
}

private fun getThumbnailName(string: String): String? {
    return string.toLowerCase()
        .replace(" ", "_")
        .replace("'", "")
        .replace("\\.", "")
        .replace("-", "_")
}