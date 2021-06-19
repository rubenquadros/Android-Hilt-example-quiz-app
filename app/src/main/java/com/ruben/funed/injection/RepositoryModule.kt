package com.ruben.funed.injection

import com.ruben.funed.data.DataSource
import com.ruben.funed.data.repository.TestRepositoryImpl
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
class RepositoryModule {

    @Provides
    fun provideRepository(dataSource: DataSource): TestRepository = TestRepositoryImpl(dataSource)
}