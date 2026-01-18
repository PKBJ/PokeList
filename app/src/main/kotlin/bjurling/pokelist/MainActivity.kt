package bjurling.pokelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import bjurling.pokelist.navigation.NavigationRoute
import bjurling.pokelist.ui.component.PokeListTopBar
import bjurling.pokelist.ui.screen.details.DetailsScreen
import bjurling.pokelist.ui.screen.list.ListScreen
import bjurling.pokelist.ui.theme.PokeListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeListTheme {
                PokeList()
            }
        }
    }
}

@Composable
private fun PokeList() {
    val backStack = remember { mutableStateListOf<NavigationRoute>(NavigationRoute.Home) }

    val currentRoute = backStack.lastOrNull()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PokeListTopBar(
                title = currentRoute.title(),
                onBack = { backStack.removeLastOrNull(); Unit }.takeIf { backStack.size > 1 }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(paddingValues = innerPadding),
            backStack = backStack,
            onBack = backStack::removeLastOrNull,
            entryProvider = entryProvider {
                entry<NavigationRoute.Home> {
                    ListScreen(
                        navigateToDetails = { resource ->
                            backStack.add(NavigationRoute.Details(resource))
                        }
                    )
                }
                entry<NavigationRoute.Details> { key ->
                    DetailsScreen(id = key.resource.id)
                }
            }
        )
    }
}

@Composable
private fun NavigationRoute?.title(): String = when (this) {
    is NavigationRoute.Home, null -> stringResource(id = R.string.app_name)
    is NavigationRoute.Details -> resource.name
}

