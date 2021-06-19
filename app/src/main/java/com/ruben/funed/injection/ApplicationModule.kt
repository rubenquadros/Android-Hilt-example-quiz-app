package com.ruben.funed.injection

import android.content.Context
import com.ruben.funed.data.DataSource
import com.ruben.funed.data.DataSourceImpl
import com.ruben.funed.remote.rest.RestApi
import com.ruben.funed.remote.rest.RestApiImpl
import com.ruben.funed.remote.retrofit.RetrofitApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

/**
 * Created by ruben.quadros on 19/06/21.
 **/
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun providesContext(@ApplicationContext context: Context) = context

    @Provides
    fun provideRestApi(retrofitApi: RetrofitApi): RestApi = RestApiImpl(retrofitApi)

    @Provides
    fun provideDataSource(restApi: RestApi): DataSource = DataSourceImpl(restApi)

    @Provides
    fun provideDispatcher() = Dispatchers.IO
}