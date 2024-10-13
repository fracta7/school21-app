package com.fracta7.school21.di

import com.fracta7.school21.data.repository.AppRepositoryImpl
import com.fracta7.school21.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun repositoryBinder(repositoryImpl: AppRepositoryImpl): AppRepository
}