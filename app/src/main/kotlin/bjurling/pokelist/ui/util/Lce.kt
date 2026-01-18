package bjurling.pokelist.ui.util

sealed class Lce<out T> {
    data object Loading : Lce<Nothing>()
    data object Error : Lce<Nothing>()
    data class Content<T>(val data: T) : Lce<T>()
}
