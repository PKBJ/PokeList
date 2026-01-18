package bjurling.pokelist.ui.preview

import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_NO
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light mode",
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark mode",
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "Scaled font 2f",
    fontScale = 2f
)
annotation class PokeListPreview