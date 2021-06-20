package com.ruben.funed.repository.fakeimplementation.success

import com.ruben.funed.cache.DatabaseManager
import com.ruben.funed.data.DataSource
import com.ruben.funed.remote.NetworkManager
import com.ruben.funed.repository.fakeimplementation.fakedatabase.FakeDataBaseManager

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class FakeSuccessDataSource: DataSource {

    override fun api(): NetworkManager = FakeSuccessManager()
    override fun database(): DatabaseManager = FakeDataBaseManager()
}