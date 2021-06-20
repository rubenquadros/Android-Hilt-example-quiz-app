package com.ruben.funed.repository.fakeimplementation.fakedatabase

import com.ruben.funed.cache.DatabaseManager
import com.ruben.funed.cache.dao.TestDao

/**
 * Created by ruben.quadros on 21/06/21.
 **/
class FakeDataBaseManager: DatabaseManager {

    override fun testDao(): TestDao {
        return FakeDao()
    }
}