package bjurling.pokelist.data.api.model

import bjurling.pokelist.data.type.ImageUrl
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiPokemonSprites(
    val other: Other
) {
    @Serializable
    data class Other(
        val `official-artwork`: OfficialArtwork
    ) {
        @Serializable
        data class OfficialArtwork(
            val front_default: ImageUrl
        )
    }
}