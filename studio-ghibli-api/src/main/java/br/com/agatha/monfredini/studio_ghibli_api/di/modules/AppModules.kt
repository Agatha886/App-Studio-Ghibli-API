package br.com.agatha.monfredini.studioghibli.di.modules

import br.com.agatha.monfredini.studioghibli.repository.ListaFilmesRepository
import br.com.agatha.monfredini.studioghibli.repository.ListaPersonagensRepository
import br.com.agatha.monfredini.studioghibli.viewmodel.ListaFilmesViewModel
import br.com.agatha.monfredini.studioghibli.viewmodel.ListaPersonagensViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val BASE_URL = "https://ghibliapi.herokuapp.com/"

val repository = module {
    single<ListaFilmesRepository> {
        ListaFilmesRepository()
    }

    single<ListaPersonagensRepository> { ListaPersonagensRepository() }
}

val model = module {
    viewModel<ListaFilmesViewModel> {
        ListaFilmesViewModel(get())
    }

    viewModel<ListaPersonagensViewModel> { ListaPersonagensViewModel(get()) }
}

