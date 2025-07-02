package br.com.agatha.monfredini.studio_ghibli_api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    val id: String,
    val name: String,
    val climate: String,
    val terrain: String,
    @SerializedName("surface_water") val surfaceWater: String,
    @SerializedName("residents") val residentsUrl: List<String>
) : Serializable {
    fun getImage(): GhibliImage = GhibliImage(name)
}