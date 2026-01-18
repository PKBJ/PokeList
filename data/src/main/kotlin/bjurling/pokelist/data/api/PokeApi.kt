package bjurling.pokelist.data.api

import bjurling.pokelist.data.api.model.ApiPokemonDetails
import bjurling.pokelist.data.api.model.ApiPokemonList
import bjurling.pokelist.data.type.PokemonId
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): ApiPokemonList

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id: PokemonId
    ): ApiPokemonDetails
}