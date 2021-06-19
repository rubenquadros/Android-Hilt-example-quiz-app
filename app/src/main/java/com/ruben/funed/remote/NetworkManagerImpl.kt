package com.ruben.funed.remote

import com.ruben.funed.remote.rest.RestApi
import javax.inject.Inject

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class NetworkManagerImpl @Inject constructor(private val restApi: RestApi): NetworkManager {

    override fun restApi(): RestApi {
        return restApi
    }
}