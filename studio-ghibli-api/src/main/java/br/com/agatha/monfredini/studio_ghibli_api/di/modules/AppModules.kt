package br.com.agatha.monfredini.studio_ghibli_api.di.modules

import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersByMovieRepository
import br.com.agatha.monfredini.studio_ghibli_api.repository.GhibliCharactersRepository
import br.com.agatha.monfredini.studio_ghibli_api.repository.MovieListRepository
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.CharactersByMovieViewModel
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.GhibliCharactersViewModel
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ghibliApiRepository = module {
    single<MovieListRepository> { MovieListRepository() }
    single<CharactersByMovieRepository> { CharactersByMovieRepository() }
    single<GhibliCharactersRepository> { GhibliCharactersRepository() }
}

val ghibliApiViewModel = module {
    viewModel<MoviesListViewModel> { MoviesListViewModel(get()) }
    viewModel<CharactersByMovieViewModel> { CharactersByMovieViewModel(get()) }
    viewModel<GhibliCharactersViewModel> { GhibliCharactersViewModel(get()) }

}

