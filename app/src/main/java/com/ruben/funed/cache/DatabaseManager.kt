package com.ruben.funed.cache

import com.ruben.funed.cache.dao.TestDao

/**
 * Created by ruben.quadros on 19/06/21.
 **/
interface DatabaseManager {

    fun testDao(): TestDao
}