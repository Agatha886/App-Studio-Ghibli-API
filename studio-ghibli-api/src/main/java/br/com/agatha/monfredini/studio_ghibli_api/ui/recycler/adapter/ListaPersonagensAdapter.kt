package br.com.agatha.monfredini.studioghibli.ui.recycler.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.agatha.monfredini.studioghibli.R
import br.com.agatha.monfredini.studioghibli.ui.model.Personagem
import kotlinx.android.synthetic.main.item_persornagens.view.*
import java.lang.Exception

class ListaPersonagensAdapter(
    private val contex: Context,
    private val lista: MutableList<Personagem> = mutableListOf()
) :
    RecyclerView.Adapter<ListaPersonagensAdapter.PersonagensViewHolder>() {

    var quandoItemClicado: (personagem: Personagem) -> Unit ={_:Personagem ->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonagensViewHolder {
        val view = LayoutInflater.from(contex).inflate(R.layout.item_persornagens, parent, false)
        return PersonagensViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonagensViewHolder, position: Int) {
        val persornagem = lista[position]
        holder.vincula(persornagem)
    }

    fun atualiza(personagensNovos: List<Personagem?>) {
        notifyItemRangeRemoved(0, lista.size)
        lista.clear()
        try {
            lista.addAll(personagensNovos as MutableList<Personagem>)
        } catch (e: Exception) {
            Log.e("ERRO AO ATUALIZAR LISTA", "atualiza: ${e.message}")
        }

    }

    override fun getItemCount(): Int {
        return lista.size
    }

    inner class PersonagensViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {

        private lateinit var personagem: Personagem

        fun vincula(personagem: Personagem) {
            this.personagem = personagem
            item.item_persornagens_textview.setText(personagem.name)
            val campoImagem = item.item_persornagens_imagem
            setImagem(personagem.name, campoImagem, contex,
                quandoResorceNaoEncontrado = quandoResorceNaoEncontrado(campoImagem, personagem),
                quandoResorceEncontrado = quandoResorceEncontrado(personagem)
            )
            item.setOnClickListener {
                quandoItemClicado(personagem)
            }
        }

        private fun quandoResorceEncontrado(personagem: Personagem): (resorce: Int) -> Unit =
            {personagem.foto = it
            }

        private fun quandoResorceNaoEncontrado(campoImagem: ImageView,personagem: Personagem): () -> Unit = {
            val semImagem = R.drawable.sem_imagem
            campoImagem.setBackgroundResource(semImagem)
            personagem.foto = semImagem
        }

    }
}