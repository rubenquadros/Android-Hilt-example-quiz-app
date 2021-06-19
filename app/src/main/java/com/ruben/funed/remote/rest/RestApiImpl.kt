package com.ruben.funed.remote.rest

import com.ruben.funed.remote.model.TestResponse
import com.ruben.funed.remote.retrofit.RetrofitApi
import javax.inject.Inject

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class RestApiImpl @Inject constructor(private val retrofitApi: RetrofitApi): RestApi {
    override suspend fun getTest(): TestResponse {
        return retrofitApi.getTest()
    }
}