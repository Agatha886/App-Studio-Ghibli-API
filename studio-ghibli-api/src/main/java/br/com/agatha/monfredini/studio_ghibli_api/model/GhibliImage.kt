package br.com.agatha.monfredini.studio_ghibli_api.model

import android.content.Context
import android.content.res.Resources
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.IMAGE_IS_NOT_EMPTY
import br.com.agatha.monfredini.studio_ghibli_api.commons.StringCommons.STRING_IS_EMPTY

open class GhibliImage(private var imageName: String) {

    init {
        require(imageName.isNotEmpty()) { IMAGE_IS_NOT_EMPTY }
    }

    fun getImageByDrawable(context: Context, imageDefault: Int): Int {
        val resource = searchByString(context.resources, context)
        return if (resource > 0) resource else imageDefault
    }

    private fun searchByString(resources: Resources, context: Context): Int {
        val name = getThumbnailName(imageName)
        return if (name != STRING_IS_EMPTY) {
            resources.getIdentifier(
                name, "drawable",
                context.packageName
            )
        } else 0
    }

//    IF string is Castle in the Sky RETURN castle_in_the_sky
    private fun getThumbnailName(string: String): String {
        return if (string.isNotEmpty()) {
            string.lowercase()
                .replace(" ", "_")
                .replace("'", "")
                .replace("\\.", "")
                .replace("-", "_")
        } else STRING_IS_EMPTY
    }
}