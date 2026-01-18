package bjurling.pokelist.data

import android.util.Log
import bjurling.pokelist.data.api.PokeApi
import bjurling.pokelist.data.api.model.ApiPokemonDetails
import bjurling.pokelist.data.api.model.ApiPokemonSprites
import bjurling.pokelist.data.cache.PokemonDetailsCache
import bjurling.pokelist.data.model.PokemonDetails
import bjurling.pokelist.data.type.ImageUrl
import bjurling.pokelist.data.type.PokemonId
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PokemonRepositoryTest {

    private val pokeApi: PokeApi = mockk()
    private val pokemonDetailsCache: PokemonDetailsCache = mockk()
    private lateinit var repository: PokemonRepository

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0
        repository = PokemonRepositoryImpl(pokeApi, pokemonDetailsCache)
    }

    @Test
    fun `getPokemonDetails returns cached details if present`() = runTest {
        val pokemonId = PokemonId(id = 1)
        val cachedDetails = PokemonDetails(
            id = pokemonId,
            name = "Bulbasaur",
            imageUrl = ImageUrl(url = "url")
        )
        every { pokemonDetailsCache.get(pokemonId) } returns cachedDetails

        val result = repository.getPokemonDetails(pokemonId)

        assertTrue(result.isSuccess)
        assertEquals(cachedDetails, result.getOrNull())
        coVerify(exactly = 0) { pokeApi.getPokemonDetails(any()) }
    }

    @Test
    fun `getPokemonDetails fetches from API and caches if not in cache`() = runTest {
        val pokemonId = PokemonId(id = 1)
        val apiDetails = ApiPokemonDetails(
            id = pokemonId,
            name = "bulbasaur",
            sprites = ApiPokemonSprites(
                other = ApiPokemonSprites.Other(
                    `official-artwork` = ApiPokemonSprites.Other.OfficialArtwork(
                        front_default = ImageUrl(url = "url")
                    )
                )
            )
        )
        every { pokemonDetailsCache.get(pokemonId) } returns null
        coEvery { pokeApi.getPokemonDetails(pokemonId) } returns apiDetails
        every { pokemonDetailsCache.add(any()) } just runs

        val result = repository.getPokemonDetails(pokemonId)

        assertTrue(result.isSuccess)
        assertEquals("Bulbasaur", result.getOrNull()?.name)
        verify { pokemonDetailsCache.add(result.getOrNull()!!) }
    }

    @Test
    fun `getPokemonDetails returns failure on API error`() = runTest {
        val pokemonId = PokemonId(id = 1)
        every { pokemonDetailsCache.get(pokemonId) } returns null
        coEvery { pokeApi.getPokemonDetails(pokemonId) } throws RuntimeException("API Error")

        val result = repository.getPokemonDetails(pokemonId)

        assertTrue(result.isFailure)
        assertEquals("API Error", result.exceptionOrNull()?.message)
    }

    private fun verify(verifyBlock: () -> Unit) {
        io.mockk.verify { verifyBlock() }
    }
}
