package br.com.agatha.monfredini.studio_ghibli_api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Species(
    val id: String,
    val name: String,
    val classification: String,
    @SerializedName("eye_colors") val eyeColors: String,
    @SerializedName("hair_colors") val hairColors: String,
    val people: List<String>
): Serializable
