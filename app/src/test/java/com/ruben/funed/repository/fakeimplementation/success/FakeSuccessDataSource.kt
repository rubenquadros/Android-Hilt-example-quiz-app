package com.ruben.funed.repository.fakeimplementation.success

import com.ruben.funed.data.DataSource
import com.ruben.funed.remote.NetworkManager

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class FakeSuccessDataSource: DataSource {

    override fun api(): NetworkManager = FakeSuccessManager()
}