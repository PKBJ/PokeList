package bjurling.pokelist.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import bjurling.pokelist.data.model.PokemonDetails
import bjurling.pokelist.data.model.PokemonResource
import bjurling.pokelist.data.api.PokeApi
import bjurling.pokelist.data.api.model.toPokemonDetails
import bjurling.pokelist.data.cache.PokemonDetailsCache
import bjurling.pokelist.data.paging.PokemonPagingSource
import bjurling.pokelist.data.type.PokemonId
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPagingPokemonList(): Flow<PagingData<PokemonResource>>
    suspend fun getPokemonDetails(id: PokemonId): Result<PokemonDetails>
}

internal class PokemonRepositoryImpl(
    private val pokeApi: PokeApi,
    private val pokemonDetailsCache: PokemonDetailsCache
) : PokemonRepository {
    override fun getPagingPokemonList(): Flow<PagingData<PokemonResource>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PokemonPagingSource(pokeApi) }
    ).flow

    override suspend fun getPokemonDetails(id: PokemonId): Result<PokemonDetails> {
        return try {
            val cachedDetails = pokemonDetailsCache.get(id)
            return if (cachedDetails != null) {
                Result.success(value = cachedDetails)
            } else {
                val apiDetails = pokeApi.getPokemonDetails(id)
                val details = apiDetails.toPokemonDetails()
                pokemonDetailsCache.add(details)
                Result.success(value = details)
            }
        } catch (e: Exception) {
            Log.e("PokemonRepository", "Error fetching details for pokemon $id", e)
            Result.failure(e)
        }
    }
}
