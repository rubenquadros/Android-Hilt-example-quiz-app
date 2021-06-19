package com.ruben.funed.data

import com.ruben.funed.remote.NetworkManager
import com.ruben.funed.remote.NetworkManagerImpl
import com.ruben.funed.remote.rest.RestApi
import javax.inject.Inject

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class DataSourceImpl @Inject constructor(private val restApi: RestApi): DataSource {

    override fun api(): NetworkManager {
        return NetworkManagerImpl(restApi)
    }
}