package com.ruben.funed.repository.fakeimplementation.success

import com.ruben.funed.remote.NetworkManager
import com.ruben.funed.remote.rest.RestApi

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class FakeSuccessManager: NetworkManager {

    override fun restApi(): RestApi = FakeSuccessApi()
}