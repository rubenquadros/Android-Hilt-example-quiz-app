package com.ruben.funed.repository.fakeimplementation.fail

import com.ruben.funed.remote.RemoteException
import com.ruben.funed.remote.model.TestResponse
import com.ruben.funed.remote.rest.RestApi

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class FakeFailApi: RestApi {
    override suspend fun getTest(): TestResponse {
        throw RemoteException.NoNetwork("No Network")
    }
}