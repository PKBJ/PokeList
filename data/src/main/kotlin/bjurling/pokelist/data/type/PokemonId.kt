package bjurling.pokelist.data.type

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class PokemonId(val id: Int) {
    override fun toString(): String = id.toString()
}
