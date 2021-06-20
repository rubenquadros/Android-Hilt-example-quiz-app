package com.ruben.funed.injection

import android.content.Context
import androidx.room.Room
import com.ruben.funed.cache.DBConstants
import com.ruben.funed.cache.TestDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by ruben.quadros on 20/06/21.
 **/
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideTestDB(context: Context): TestDB =
        Room.databaseBuilder(context, TestDB::class.java, DBConstants.NAME)
            .fallbackToDestructiveMigration().build()
}