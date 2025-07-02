package br.com.agatha.monfredini.studio_ghibli_api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    val title: String,
    val description: String,
    @SerializedName("release_date") val releaseDate: String,
    val people: List<String>,
    val species: List<String>,
) : Serializable {
    fun getImage(): GhibliImage = GhibliImage(title)
}
