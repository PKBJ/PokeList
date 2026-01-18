package bjurling.pokelist.data.type

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class ImageUrl(val url: String) {
    override fun toString(): String = url
}