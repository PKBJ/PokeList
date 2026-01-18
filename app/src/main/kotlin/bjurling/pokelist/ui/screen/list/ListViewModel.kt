package bjurling.pokelist.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import bjurling.pokelist.data.PokemonRepository

internal class ListViewModel(
    pokemonRepository: PokemonRepository
) : ViewModel() {

    val pokemonPagingData = pokemonRepository.getPagingPokemonList().cachedIn(viewModelScope)
}
