package bjurling.pokelist.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bjurling.pokelist.data.PokemonRepository
import bjurling.pokelist.data.model.PokemonDetails
import bjurling.pokelist.data.type.PokemonId
import bjurling.pokelist.ui.util.Lce
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class DetailsViewModel(
    private val id: PokemonId,
    private val repository: PokemonRepository
) : ViewModel() {

    val uiState: StateFlow<DetailsUiState>
        field = MutableStateFlow<DetailsUiState>(value = DetailsUiState.Initial)

    init {
        viewModelScope.launch {
            uiState.update { it.copy(lcePokemonDetails = Lce.Loading) }
            repository.getPokemonDetails(id)
                .onSuccess { details ->
                    uiState.update { it.copy(lcePokemonDetails = Lce.Content(data = details)) }
                }
                .onFailure {
                    uiState.update { it.copy(lcePokemonDetails = Lce.Error) }
                }
        }
    }
}

internal data class DetailsUiState(
    val lcePokemonDetails: Lce<PokemonDetails>
) {
    companion object {
        val Initial = DetailsUiState(lcePokemonDetails = Lce.Loading)
    }
}
