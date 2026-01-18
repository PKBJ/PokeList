package bjurling.pokelist.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bjurling.pokelist.R
import bjurling.pokelist.ui.preview.PokeListPreview
import bjurling.pokelist.ui.theme.PokeListPreviewTheme

@Composable
fun PokeListTopBar(
    title: String,
    onBack: (() -> Unit)?,
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = if (onBack != null) 48.dp else 12.dp)
                    .semantics { heading() },
                text = title,
                textAlign = TextAlign.Center,
            )
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                    )
                }
            }
        }
    )
}

@PokeListPreview
@Composable
private fun PreviewPokeListTopBar() {
    PokeListPreviewTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            PokeListTopBar(
                title = "Title with back button",
                onBack = {},
            )
            PokeListTopBar(
                title = "Title without back button",
                onBack = null,
            )
        }
    }
}