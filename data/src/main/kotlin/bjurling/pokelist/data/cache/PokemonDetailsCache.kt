package bjurling.pokelist.data.cache

import bjurling.pokelist.data.model.PokemonDetails
import bjurling.pokelist.data.type.PokemonId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal interface PokemonDetailsCache {
    fun get(id: PokemonId): PokemonDetails?
    fun add(details: PokemonDetails)
}

internal class PokemonDetailsCacheImpl : PokemonDetailsCache {

    private val pokemonDetailsCache: MutableStateFlow<Map<PokemonId, PokemonDetails>> =
        MutableStateFlow(value = emptyMap())

    override fun get(id: PokemonId): PokemonDetails? = pokemonDetailsCache.value[id]

    override fun add(details: PokemonDetails) {
        pokemonDetailsCache.update { it.plus(pair = details.id to details) }
    }
}
