package bjurling.pokelist.navigation

import androidx.navigation3.runtime.NavKey
import bjurling.pokelist.data.model.PokemonResource

sealed class NavigationRoute : NavKey {

    data object Home : NavigationRoute()

    data class Details(val resource: PokemonResource) : NavigationRoute()
}