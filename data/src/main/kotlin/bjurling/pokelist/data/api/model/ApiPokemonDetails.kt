package bjurling.pokelist.data.api.model

import bjurling.pokelist.data.model.PokemonDetails
import bjurling.pokelist.data.type.PokemonId
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiPokemonDetails(
    val id: PokemonId,
    val name: String,
    val sprites: ApiPokemonSprites
)

internal fun ApiPokemonDetails.toPokemonDetails() = PokemonDetails(
    id = id,
    name = name.replaceFirstChar { it.uppercase() },
    imageUrl = sprites.other.`official-artwork`.front_default
)
