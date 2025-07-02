package br.com.agatha.monfredini.studio_ghibli_api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GhibliCharacter(
    val name: String,
    val gender: String,
    val age: String,
    @SerializedName("hair_color") val hairColor: String,
    @SerializedName("eye_color") val eyeColor: String,
) : Serializable {
    fun getImage(): GhibliImage = GhibliImage(name)
}