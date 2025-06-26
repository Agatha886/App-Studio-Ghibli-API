package br.com.agatha.monfredini.studioghibli.ui.model

import java.io.Serializable

class Personagem(
    val name: String,
    val gender: String,
    val age: String,
    val hair_color: String,
    val eye_color: String,
    var foto: Int
) : Serializable