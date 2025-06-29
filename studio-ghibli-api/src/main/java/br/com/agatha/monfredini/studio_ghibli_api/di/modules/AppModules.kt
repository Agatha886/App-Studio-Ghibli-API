package br.com.agatha.monfredini.studio_ghibli_api.di.modules

import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersByMovieRepository
import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersRepository
import br.com.agatha.monfredini.studio_ghibli_api.repository.MovieListRepository
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.CharactersByMovieViewModel
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.CharactersViewModel
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ghibliApiRepository = module {
    single<MovieListRepository> { MovieListRepository() }
    single<CharactersByMovieRepository> { CharactersByMovieRepository() }
    single<CharactersRepository> { CharactersRepository() }
}

val ghibliApiViewModel = module {
    viewModel<MoviesListViewModel> { MoviesListViewModel(get()) }
    viewModel<CharactersByMovieViewModel> { CharactersByMovieViewModel(get()) }
    viewModel<CharactersViewModel> { CharactersViewModel(get()) }

}

