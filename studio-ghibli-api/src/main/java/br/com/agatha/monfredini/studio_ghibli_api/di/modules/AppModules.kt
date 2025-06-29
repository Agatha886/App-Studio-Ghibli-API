package br.com.agatha.monfredini.studio_ghibli_api.di.modules

import br.com.agatha.monfredini.studio_ghibli_api.repository.MovieListRepository
import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersRepository
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.MoviesListViewModel
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.DetailsMovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal const val BASE_URL = "https://ghibliapi.vercel.app"

val repository = module {
    single<MovieListRepository> {
        MovieListRepository()
    }

    single<CharactersRepository> { CharactersRepository() }
}

val model = module {
    viewModel<MoviesListViewModel> {
        MoviesListViewModel(get())
    }

    viewModel<DetailsMovieViewModel> { DetailsMovieViewModel(get()) }
}

