package com.feeding.tracker.data.di

import com.feeding.tracker.data.datasource.AuthFirebaseDataSource
import com.feeding.tracker.data.repository.AuthRepositoryImpl
import com.feeding.tracker.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideAuthRepository(dataSource: AuthFirebaseDataSource): AuthRepository = AuthRepositoryImpl(dataSource)
}