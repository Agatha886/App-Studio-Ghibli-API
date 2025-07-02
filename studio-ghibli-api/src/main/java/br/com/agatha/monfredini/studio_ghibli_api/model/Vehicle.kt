package br.com.agatha.monfredini.studio_ghibli_api.model

import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.BASE_URL
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Vehicle(
    val id: String,
    val name: String,
    val description: String,
    val vehicle_class: String,
    val length: String,
    @SerializedName("pilot") private val pilotUrl: String,
    var pilotCharacter: GhibliCharacter? = null
) : Serializable {
    val pilotId: String
        get() = pilotUrl.replace("$BASE_URL/people/", "")

    fun getImage(): GhibliImage = GhibliImage(name)
}