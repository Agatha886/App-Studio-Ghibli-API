package br.com.agatha.monfredini.studioghibli.ui.recycler.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.agatha.monfredini.studioghibli.R
import br.com.agatha.monfredini.studioghibli.ui.model.Filme
import kotlinx.android.synthetic.main.item_filme.view.*

class ListaFilmesAdapter(
    private val contex: Context,
    private val lista: MutableList<Filme> = mutableListOf()
) : RecyclerView.Adapter<ListaFilmesAdapter.ListaFilmesAdapterViewModel>() {

    var quandoItemClicado: (filme: Filme) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaFilmesAdapterViewModel {
        val view = LayoutInflater.from(contex).inflate(R.layout.item_filme, parent, false)
        return ListaFilmesAdapterViewModel(view)
    }

    override fun onBindViewHolder(holder: ListaFilmesAdapterViewModel, position: Int) {
        val filme = lista[position]
        holder.vincula(filme)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun atualiza(filmesNovos: List<Filme>?) {
        notifyItemRangeRemoved(0, lista.size)
        lista.clear()
        if (filmesNovos != null) {
            lista.addAll(filmesNovos)
        } else {
            Log.e("NULL", "FILMES NULOS")
        }

        notifyItemRangeInserted(0, lista.size)
    }

    inner class ListaFilmesAdapterViewModel(item: View) : RecyclerView.ViewHolder(item) {
        private val campoTitulo by lazy { item.item_filmes_titulo }
        private val campoAno by lazy { item.item_filmes_ano }
        private val campoImagem by lazy { item.item_filmes_imagem }
        private lateinit var filme: Filme

        init {
            item.setOnClickListener {
                if (::filme.isInitialized) {
                    quandoItemClicado(filme)
                }
            }
        }

        fun vincula(filme: Filme) {
            this.filme = filme
            campoTitulo.setText(filme.title)
            campoAno.setText(filme.release_date)
            setImagem(
                filme.title,
                campoImagem,
                contex,
                quandoResorceEncontrado = quandoResorceEncontrado(filme),
                quandoResorceNaoEncontrado = quandoResorceNaoEncontrado(filme)
            )

        }

        private fun quandoResorceNaoEncontrado(filme: Filme): () -> Unit = {
                val semImagem = R.drawable.sem_imagem
                campoImagem.setBackgroundResource(semImagem)
                filme.poster = semImagem
        }

        private fun quandoResorceEncontrado(filme: Filme): (resorce: Int) -> Unit =
            {
                filme.poster = it
            }
    }
}

