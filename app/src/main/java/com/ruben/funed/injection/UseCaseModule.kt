package com.ruben.funed.injection

import com.ruben.funed.domain.interactor.GetAnswersUseCase
import com.ruben.funed.domain.interactor.GetTestUseCase
import com.ruben.funed.domain.interactor.UpdateMcqAnswerUseCase
import com.ruben.funed.domain.interactor.UpdateShortAnswerUseCase
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

    @Provides
    fun provideUpdateMcqAnswerUseCase(testRepository: TestRepository): UpdateMcqAnswerUseCase = UpdateMcqAnswerUseCase(testRepository)

    @Provides
    fun provideUpdateShortAnswerUseCase(testRepository: TestRepository): UpdateShortAnswerUseCase = UpdateShortAnswerUseCase(testRepository)

    @Provides
    fun provideGetAnswersUseCase(testRepository: TestRepository): GetAnswersUseCase = GetAnswersUseCase(testRepository)
}