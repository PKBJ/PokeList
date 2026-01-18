package bjurling.pokelist.data.cache

import bjurling.pokelist.data.model.PokemonDetails
import bjurling.pokelist.data.type.PokemonId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal interface PokemonDetailsCache {
    fun get(pokemonId: PokemonId): PokemonDetails?
    fun add(pokemonDetails: PokemonDetails)
}

internal class PokemonDetailsCacheImpl : PokemonDetailsCache {

    private val pokemonDetailsCache: MutableStateFlow<Map<PokemonId, PokemonDetails>> =
        MutableStateFlow(value = emptyMap())

    override fun get(pokemonId: PokemonId): PokemonDetails? = pokemonDetailsCache.value[pokemonId]

    override fun add(pokemonDetails: PokemonDetails) {
        pokemonDetailsCache.update { it.plus(pair = pokemonDetails.id to pokemonDetails) }
    }
}
