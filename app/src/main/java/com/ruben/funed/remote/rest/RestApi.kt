package com.ruben.funed.remote.rest

import com.ruben.funed.remote.model.TestResponse

/**
 * Created by ruben.quadros on 19/06/21.
 **/
interface RestApi {
    suspend fun getTest(): TestResponse
}