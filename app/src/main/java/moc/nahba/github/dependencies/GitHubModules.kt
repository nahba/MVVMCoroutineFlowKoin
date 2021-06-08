package moc.nahba.github.dependencies

import android.content.Context
import android.content.res.Resources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moc.nahba.github.common.StringResourceManager
import moc.nahba.github.common.impls.StringResourceManagerImpl
import moc.nahba.github.constants.BASE_URL
import moc.nahba.github.constants.TIME_OUT
import moc.nahba.github.models.datasources.GitHubRemoteSource
import moc.nahba.github.models.repositories.GitHubRepository
import moc.nahba.github.models.services.GitHubApi
import moc.nahba.github.models.usecases.GitHubUseCase
import moc.nahba.github.views.viewmodels.GitHubViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private fun provideResources(context: Context): Resources = context.resources
val ManagerModule = module(override = true, createdAtStart = true) {
    single { provideResources(androidContext()) }
}

private fun provideStringResourceManager(resources: Resources): StringResourceManager = StringResourceManagerImpl(resources)
val HelperModule = module(override = true, createdAtStart = true) {
    single { provideStringResourceManager(get()) }
}

private fun provideConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()
private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
    .build()
private fun providesRetrofit(okHttpClient: OkHttpClient, converterFactory: MoshiConverterFactory): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(converterFactory)
    .build()
val NetworkModule = module(override = true, createdAtStart = true) {
    single { provideOkHttpClient() }
    single { provideConverterFactory() }
    single { providesRetrofit(get(), get()) }
}

private fun provideApiService(retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)
val ServiceModule = module(override = true, createdAtStart = true) {
    single { provideApiService(get()) }
}

private fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
private fun provideDataSource(apiService: GitHubApi, dispatcher: CoroutineDispatcher) =
    GitHubRemoteSource(apiService, dispatcher)
private fun provideRepository(dataSource: GitHubRemoteSource): GitHubRepository = GitHubRepository(dataSource)
private fun provideUseCase(repository: GitHubRepository): GitHubUseCase = GitHubUseCase(repository)
val ModelModule = module(override = true, createdAtStart = true) {
    single() { provideDispatcher() }
    single { provideDataSource(get(), get()) }
    single { provideRepository(get()) }
    single { provideUseCase(get()) }
}

val ViewModelModule = module(override = true) {
    viewModel { GitHubViewModel(get()) }
}