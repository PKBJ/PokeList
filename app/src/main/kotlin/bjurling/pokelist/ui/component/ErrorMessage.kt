package bjurling.pokelist.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bjurling.pokelist.ui.preview.PokeListPreview
import bjurling.pokelist.ui.theme.PokeListPreviewTheme

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(size = 8.dp),
        color = MaterialTheme.colorScheme.error,
        shadowElevation = 4.dp,
    ) {
        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = message,
            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onError,
        )
    }
}

@PokeListPreview
@Composable
private fun PreviewErrorMessage() {
    PokeListPreviewTheme {
        ErrorMessage(
            message = "Something went wrong"
        )
    }
}