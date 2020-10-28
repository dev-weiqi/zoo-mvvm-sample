package dev.weiqi.zoo.di

import dev.weiqi.zoo.network.api.ApiClient
import dev.weiqi.zoo.network.api.ApiService
import dev.weiqi.zoo.repository.RemoteRepository
import dev.weiqi.zoo.repository.impl.RemoteRepositoryImpl
import dev.weiqi.zoo.ui.areadetail.AreaDetailViewModel
import dev.weiqi.zoo.ui.areainfo.AreaInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { ApiClient.createRetrofitService(ApiService::class) }
}

val repositoryModule = module {
    factory<RemoteRepository> { RemoteRepositoryImpl() }
}

val viewModelModule = module {
    viewModel { AreaInfoViewModel() }
    viewModel { AreaDetailViewModel() }
}

val koinModuleList = listOf(networkModule, repositoryModule, viewModelModule)