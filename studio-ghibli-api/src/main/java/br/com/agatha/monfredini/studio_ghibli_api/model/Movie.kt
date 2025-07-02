package br.com.agatha.monfredini.studio_ghibli_api.model

import java.io.Serializable

data class Movie(
    val title: String,
    val description: String,
    val release_date: String,
    val people: List<String>,
    val species: List<String>,
) : Serializable, GhibliImage(title)
