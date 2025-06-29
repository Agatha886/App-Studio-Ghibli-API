package br.com.agatha.monfredini.studio_ghibli_api.di.modules

import br.com.agatha.monfredini.studio_ghibli_api.repository.MovieListRepository
import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersListRepository
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.MoviesListViewModel
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.CharactersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal const val BASE_URL = "https://ghibliapi.vercel.app"

val repository = module {
    single<MovieListRepository> {
        MovieListRepository()
    }

    single<CharactersListRepository> { CharactersListRepository() }
}

val model = module {
    viewModel<MoviesListViewModel> {
        MoviesListViewModel(get())
    }

    viewModel<CharactersListViewModel> { CharactersListViewModel(get()) }
}

