package jp.co.yumemi.android.codecheck.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import jp.co.yumemi.android.codecheck.data.remote.Api
import jp.co.yumemi.android.codecheck.data.remote.GitHubApi
import jp.co.yumemi.android.codecheck.data.remote.ItemRemoteDataSource
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideClient(): HttpClient {
        return HttpClient(Android)
    }

    @Provides
    @Singleton
    fun provideApi(client: HttpClient): Api {
        return GitHubApi(client)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: Api): ItemRemoteDataSource {
        return ItemRemoteDataSource(api, Dispatchers.IO)
    }
}