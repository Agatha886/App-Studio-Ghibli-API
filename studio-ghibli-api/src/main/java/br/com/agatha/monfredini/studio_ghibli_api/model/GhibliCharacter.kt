package br.com.agatha.monfredini.studio_ghibli_api.model

import java.io.Serializable

data class GhibliCharacter(
    val name: String,
    val gender: String,
    val age: String,
    val hair_color: String,
    val eye_color: String,
    var photo: Int
) : Serializable {
    fun getImage(): GhibliImage = GhibliImage(name)
}