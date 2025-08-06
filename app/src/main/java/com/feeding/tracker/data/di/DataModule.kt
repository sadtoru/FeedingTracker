package com.feeding.tracker.data.di

import com.feeding.tracker.data.datasource.AuthFirebaseDataSource
import com.feeding.tracker.data.datasource.FeedingFirebaseDataSource
import com.feeding.tracker.data.repository.AuthRepositoryImpl
import com.feeding.tracker.data.repository.RealtimeRepositoryImpl
import com.feeding.tracker.domain.repository.AuthRepository
import com.feeding.tracker.domain.repository.RealtimeRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(dataSource: AuthFirebaseDataSource): AuthRepository = AuthRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideFeedingFirebaseDataSource(): FeedingFirebaseDataSource = FeedingFirebaseDataSource()

    @Provides
    @Singleton
    fun provideRealtimeRepository(dataSource: FeedingFirebaseDataSource): RealtimeRepository = RealtimeRepositoryImpl(dataSource)
}
