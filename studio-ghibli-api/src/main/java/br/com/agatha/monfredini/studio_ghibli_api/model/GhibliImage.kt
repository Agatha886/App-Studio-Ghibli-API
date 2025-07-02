package br.com.agatha.monfredini.studio_ghibli_api.model

import android.content.Context
import android.content.res.Resources
import java.util.Locale

open class GhibliImage(val imageName: String) {
    fun getImage(resources: Resources, contex: Context, imageDefault: Int): Int {
        val searchByResources = searchByResources(resources, contex)
        return if (searchByResources > 0) searchByResources else imageDefault
    }

    private fun searchByResources(resources: Resources, contex: Context): Int {
        val imageName = getThumbnailName(imageName)
        return resources.getIdentifier(
            imageName, "drawable",
            contex.packageName
        )
    }

    private fun getThumbnailName(string: String): String {
        return string.toLowerCase(Locale.ROOT)
            .replace(" ", "_")
            .replace("'", "")
            .replace("\\.", "")
            .replace("-", "_")
    }
}