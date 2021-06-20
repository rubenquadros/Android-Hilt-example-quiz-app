package com.ruben.funed.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ruben.funed.cache.dao.TestDao
import com.ruben.funed.cache.entity.TestEntity

/**
 * Created by ruben.quadros on 20/06/21.
 **/
@Database(entities = [TestEntity::class], version = DBConstants.VERSION)
abstract class TestDB: RoomDatabase() {

    abstract fun testDao(): TestDao

    companion object {

        @Volatile
        private var INSTANCE: TestDB? = null

        fun getInstance(context: Context): TestDB = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, TestDB::class.java, DBConstants.NAME
        ).fallbackToDestructiveMigration().build()

    }
}