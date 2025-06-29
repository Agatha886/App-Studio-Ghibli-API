package br.com.agatha.monfredini.studio_ghibli_api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Vehicle(
    val id: String,
    val name: String,
    val description: String,
    val vehicle_class: String,
    val length: String,
    @SerializedName("pilot") val pilotUrl: String,
    var pilotCharacter: GhibliCharacter? = null
) : Serializable