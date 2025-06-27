package br.com.agatha.monfredini.studio_ghibli_api.model

import java.io.Serializable

data class GhibliCharacter(
    val name: String,
    val gender: String,
    val age: String,
    val hair_color: String,
    val eye_color: String,
    var foto: Int
) : Serializable