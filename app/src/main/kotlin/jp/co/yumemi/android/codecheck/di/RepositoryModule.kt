package jp.co.yumemi.android.codecheck.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import jp.co.yumemi.android.codecheck.ItemRepository
import jp.co.yumemi.android.codecheck.ItemRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(impl: ItemRepositoryImpl): ItemRepository
}