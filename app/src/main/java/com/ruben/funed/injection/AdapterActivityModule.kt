package com.ruben.funed.injection

import com.ruben.funed.presentation.submission.AnswersAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Created by ruben.quadros on 20/06/21.
 **/
@Module
@InstallIn(ActivityComponent::class)
class AdapterActivityModule {

    @Provides
    fun provideAnswersAdapter() = AnswersAdapter()
}