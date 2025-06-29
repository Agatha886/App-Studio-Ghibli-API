package br.com.agatha.monfredini.studio_ghibli_api.model

import java.io.Serializable

data class Species(
    val id: String,
    val name: String,
    val classification: String,
    val eye_colors: String,
    val hair_colors: String,
    val people: List<String>
): Serializable
