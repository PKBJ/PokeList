package bjurling.pokelist.ui.screen.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import bjurling.pokelist.R
import bjurling.pokelist.data.model.PokemonResource
import bjurling.pokelist.data.type.PokemonId
import bjurling.pokelist.ui.component.ErrorMessage
import bjurling.pokelist.ui.preview.PokeListPreview
import bjurling.pokelist.ui.theme.PokeListPreviewTheme
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ListScreen(
    viewModel: ListViewModel = koinViewModel(),
    navigateToDetails: (PokemonResource) -> Unit,
) {
    val pagingItems = viewModel.pokemonPagingData.collectAsLazyPagingItems()
    ListScreen(
        pagingItems = pagingItems,
        navigateToDetails = navigateToDetails,
    )
}

@Composable
private fun ListScreen(
    pagingItems: LazyPagingItems<PokemonResource>,
    navigateToDetails: (PokemonResource) -> Unit,
) {
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = pagingItems.loadState.refresh is LoadState.Loading,
        onRefresh = pagingItems::refresh,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let { pokemon ->
                    Text(
                        text = pokemon.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigateToDetails(pokemon) }
                            .padding(all = 16.dp)
                    )
                    HorizontalDivider()
                }
            }

            with(pagingItems) {
                when {
                    loadState.append is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(all = 16.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        item {
                            PagingError(
                                message = stringResource(id = R.string.list_error),
                            )
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        item {
                            PagingError(
                                message = stringResource(id = R.string.list_error_append),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PagingError(
    message: String,
    modifier: Modifier = Modifier,
) {
    ErrorMessage(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 24.dp),
        message = message
    )
}

@PokeListPreview
@Composable
private fun PreviewListScreen() {
    PokeListPreviewTheme {
        val pagingItems = flowOf(
            value = PagingData.from(
                data = listOf(
                    PokemonResource(id = PokemonId(1), name = "Bulbasaur"),
                    PokemonResource(id = PokemonId(2), name = "Ivysaur"),
                    PokemonResource(id = PokemonId(3), name = "Venusaur"),
                )
            )
        ).collectAsLazyPagingItems()
        ListScreen(
            pagingItems = pagingItems,
            navigateToDetails = {}
        )
    }
}

@PokeListPreview
@Composable
private fun PreviewListScreenRefreshError() {
    PokeListPreviewTheme {
        val errorLoadState = LoadState.Error(IllegalStateException("Error"))
        val pagingItems = flowOf(
            value = PagingData.from(
                data = emptyList<PokemonResource>(),
                sourceLoadStates = LoadStates(
                    refresh = errorLoadState,
                    append = errorLoadState,
                    prepend = errorLoadState,
                )
            )
        ).collectAsLazyPagingItems()
        ListScreen(
            pagingItems = pagingItems,
            navigateToDetails = {}
        )
    }
}


@PokeListPreview
@Composable
private fun PreviewListScreenAppendError() {
    PokeListPreviewTheme {
        val pagingItems = flowOf(
            value = PagingData.from(
                data = listOf(
                    PokemonResource(id = PokemonId(1), name = "Bulbasaur"),
                    PokemonResource(id = PokemonId(2), name = "Ivysaur"),
                    PokemonResource(id = PokemonId(3), name = "Venusaur"),
                ),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(endOfPaginationReached = false),
                    append = LoadState.Error(IllegalStateException("Error")),
                    prepend = LoadState.Error(IllegalStateException("Error")),
                )
            )
        ).collectAsLazyPagingItems()
        ListScreen(
            pagingItems = pagingItems,
            navigateToDetails = {}
        )
    }
}
