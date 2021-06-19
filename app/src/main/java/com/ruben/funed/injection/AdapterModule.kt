package com.ruben.funed.injection

import com.ruben.funed.presentation.test.OptionsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

/**
 * Created by ruben.quadros on 19/06/21.
 **/
@Module
@InstallIn(FragmentComponent::class)
class AdapterModule {

  @Provides
  fun provideOptionsAdapter() = OptionsAdapter()
}