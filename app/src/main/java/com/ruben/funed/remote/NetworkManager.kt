package com.ruben.funed.remote

import com.ruben.funed.remote.rest.RestApi

/**
 * Created by ruben.quadros on 19/06/21.
 **/
interface NetworkManager {
    fun restApi(): RestApi
}