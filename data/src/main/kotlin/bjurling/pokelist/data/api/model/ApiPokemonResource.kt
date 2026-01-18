package bjurling.pokelist.data.api.model

import kotlinx.serialization.Serializable

@Serializable
internal data class ApiPokemonResource(
    val name: String,
    val url: String
)