package com.ruben.funed.cache

import com.ruben.funed.cache.dao.TestDao

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class DatabaseManagerImpl (private val testDB: TestDB): DatabaseManager {

    override fun testDao(): TestDao {
        return testDB.testDao()
    }
}