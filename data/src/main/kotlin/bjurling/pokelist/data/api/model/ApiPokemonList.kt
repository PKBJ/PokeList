package bjurling.pokelist.data.api.model

import kotlinx.serialization.Serializable

@Serializable
internal data class ApiPokemonList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ApiPokemonResource>
)