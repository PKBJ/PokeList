package bjurling.pokelist.data.model

import bjurling.pokelist.data.type.ImageUrl
import bjurling.pokelist.data.type.PokemonId

data class PokemonDetails(
    val id: PokemonId,
    val name: String,
    val imageUrl: ImageUrl
)
