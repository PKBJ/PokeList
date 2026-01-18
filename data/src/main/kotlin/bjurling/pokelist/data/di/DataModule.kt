package bjurling.pokelist.data.di

import bjurling.pokelist.data.PokemonRepository
import bjurling.pokelist.data.PokemonRepositoryImpl
import bjurling.pokelist.data.api.PokeApi
import bjurling.pokelist.data.cache.PokemonDetailsCacheImpl
import bjurling.pokelist.data.cache.PokemonDetailsCache
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val dataModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(get())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    single {
        get<Retrofit>().create(PokeApi::class.java)
    }

    single<PokemonRepository> {
        PokemonRepositoryImpl(
            pokeApi = get(),
            pokemonDetailsCache = get()
        )
    }
    singleOf<PokemonDetailsCache>(constructor = ::PokemonDetailsCacheImpl)
}
