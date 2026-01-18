package bjurling.pokelist.ui.screen.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bjurling.pokelist.R
import bjurling.pokelist.data.model.PokemonDetails
import bjurling.pokelist.data.type.ImageUrl
import bjurling.pokelist.data.type.PokemonId
import bjurling.pokelist.ui.component.ErrorMessage
import bjurling.pokelist.ui.preview.PokeListPreview
import bjurling.pokelist.ui.theme.PokeListPreviewTheme
import bjurling.pokelist.ui.util.Lce
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun DetailsScreen(
    id: PokemonId,
    viewModel: DetailsViewModel = koinViewModel(key = id.toString()) { parametersOf(id) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DetailsScreen(
        uiState = uiState,
    )
}

@Composable
private fun DetailsScreen(
    uiState: DetailsUiState,
) {
    when (val lceDetails = uiState.lcePokemonDetails) {
        is Lce.Loading -> CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        )

        is Lce.Content -> {
            val isPreview = LocalInspectionMode.current
            val details = lceDetails.data
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center),
                model = if (isPreview) {
                    R.drawable.ic_pokeball
                } else {
                    details.imageUrl.url
                },
                contentDescription = details.name,
            )
        }

        is Lce.Error -> {
            ErrorMessage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                message = stringResource(id = R.string.details_error)
            )
        }
    }
}

@PokeListPreview
@Composable
private fun PreviewDetailsScreenContent() {
    PokeListPreviewTheme {
        DetailsScreen(
            uiState = DetailsUiState(
                lcePokemonDetails = Lce.Content(
                    PokemonDetails(
                        id = PokemonId(35),
                        name = "Clearify",
                        imageUrl = ImageUrl(url = ""),
                    )
                )
            ),
        )
    }
}

@PokeListPreview
@Composable
private fun PreviewDetailsScreenLoading() {
    PokeListPreviewTheme {
        DetailsScreen(uiState = DetailsUiState(lcePokemonDetails = Lce.Loading))
    }
}

@PokeListPreview
@Composable
private fun PreviewDetailsScreenError() {
    PokeListPreviewTheme {
        DetailsScreen(uiState = DetailsUiState(lcePokemonDetails = Lce.Error))
    }
}

