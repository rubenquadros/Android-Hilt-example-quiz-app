package com.ruben.funed.data

import android.content.Context
import com.ruben.funed.cache.DatabaseManager
import com.ruben.funed.cache.DatabaseManagerImpl
import com.ruben.funed.cache.TestDB
import com.ruben.funed.remote.NetworkManager
import com.ruben.funed.remote.NetworkManagerImpl
import com.ruben.funed.remote.rest.RestApi
import javax.inject.Inject

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class DataSourceImpl @Inject constructor(
    private val restApi: RestApi,
    private val context: Context
) : DataSource {

    override fun api(): NetworkManager {
        return NetworkManagerImpl(restApi)
    }

    override fun database(): DatabaseManager {
        return DatabaseManagerImpl(TestDB.getInstance(context))
    }
}