package com.ruben.funed.injection

import com.ruben.funed.domain.interactor.GetTestUseCase
import com.ruben.funed.domain.repository.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by ruben.quadros on 19/06/21.
 **/
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetTestUseCase(testRepository: TestRepository): GetTestUseCase = GetTestUseCase(testRepository)
}