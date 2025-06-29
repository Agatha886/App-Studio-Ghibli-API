package br.com.agatha.monfredini.studio_ghibli_api.di.modules

import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersByMovieRepository
import br.com.agatha.monfredini.studio_ghibli_api.repository.CharactersRepository
import br.com.agatha.monfredini.studio_ghibli_api.repository.MoviesRepository
import br.com.agatha.monfredini.studio_ghibli_api.repository.VehiclesRepository
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.CharactersByMovieViewModel
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.CharactersViewModel
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.MoviesViewModel
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.VehiclesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ghibliApiRepository = module {
    single<MoviesRepository> { MoviesRepository() }
    single<CharactersByMovieRepository> { CharactersByMovieRepository() }
    single<CharactersRepository> { CharactersRepository() }
    single<VehiclesRepository> { VehiclesRepository() }
}

val ghibliApiViewModel = module {
    viewModel<MoviesViewModel> { MoviesViewModel(get()) }
    viewModel<CharactersByMovieViewModel> { CharactersByMovieViewModel(get()) }
    viewModel<CharactersViewModel> { CharactersViewModel(get()) }
    viewModel<VehiclesViewModel> { VehiclesViewModel(get()) }
}

