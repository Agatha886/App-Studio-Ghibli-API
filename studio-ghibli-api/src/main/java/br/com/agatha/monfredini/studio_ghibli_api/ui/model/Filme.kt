package br.com.agatha.monfredini.studioghibli.ui.model

import java.io.Serializable

class Filme(
    val title: String,
    val description: String,
    val release_date: String,
    var poster: Int,
    val people: List<String>,
    var listaPersonagens: MutableList<Personagem> = mutableListOf()
):Serializable{

}