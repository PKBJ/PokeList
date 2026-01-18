package bjurling.pokelist.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import bjurling.pokelist.data.api.PokeApi
import bjurling.pokelist.data.model.PokemonResource
import bjurling.pokelist.data.type.PokemonId

internal class PokemonPagingSource(
    private val pokeApi: PokeApi
) : PagingSource<Int, PokemonResource>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonResource>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResource> {
        val offset = params.key ?: 0
        val limit = params.loadSize
        return try {
            val response = pokeApi.getPokemonList(limit = limit, offset = offset)
            LoadResult.Page(
                data = response.results.mapNotNull { pokemon ->
                    pokemon.url
                        .split("/")
                        .lastOrNull { it.isNotEmpty() }
                        ?.toIntOrNull()
                        ?.let { pokemonId ->
                            PokemonResource(
                                id = PokemonId(id = pokemonId),
                                name = pokemon.name.replaceFirstChar { it.uppercase() },
                            )
                        }
                },
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (response.next == null) null else offset + limit
            )
        } catch (e: Exception) {
            Log.e("PokemonPagingSource", "Failed to load pokemons for offset: $offset", e)
            LoadResult.Error(throwable = e)
        }
    }
}