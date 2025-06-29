package br.com.agatha.monfredini.studio_ghibli_api.model

import java.io.Serializable

data class Location(
    val id: String,
    val name: String,
    val climate: String,
    val terrain: String,
    val surface_water: String,
    val residents: List<String>
): Serializable
