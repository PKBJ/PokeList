package bjurling.pokelist.di

import bjurling.pokelist.ui.screen.details.DetailsViewModel
import bjurling.pokelist.ui.screen.list.ListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val appModule = module {
    viewModelOf(constructor = ::DetailsViewModel)
    viewModelOf(constructor = ::ListViewModel)
}
