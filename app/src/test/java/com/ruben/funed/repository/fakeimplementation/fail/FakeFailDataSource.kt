package com.ruben.funed.repository.fakeimplementation.fail

import com.ruben.funed.cache.DatabaseManager
import com.ruben.funed.data.DataSource
import com.ruben.funed.remote.NetworkManager
import com.ruben.funed.repository.fakeimplementation.fakedatabase.FakeDataBaseManager

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class FakeFailDataSource: DataSource {

    override fun api(): NetworkManager = FakeFailManager()
    override fun database(): DatabaseManager = FakeDataBaseManager()
}