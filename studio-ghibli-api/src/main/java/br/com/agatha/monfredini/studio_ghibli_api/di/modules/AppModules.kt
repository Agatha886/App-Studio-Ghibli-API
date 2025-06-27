package br.com.agatha.monfredini.studio_ghibli_api.di.modules

import br.com.agatha.monfredini.studio_ghibli_api.repository.MovieListRepository
import br.com.agatha.monfredini.studio_ghibli_api.repository.CharacterListRepository
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.MoviesListViewModel
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.CharactersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val BASE_URL = "https://ghibliapi.herokuapp.com/"

val repository = module {
    single<MovieListRepository> {
        MovieListRepository()
    }

    single<CharacterListRepository> { CharacterListRepository() }
}

val model = module {
    viewModel<MoviesListViewModel> {
        MoviesListViewModel(get())
    }

    viewModel<CharactersListViewModel> { CharactersListViewModel(get()) }
}

