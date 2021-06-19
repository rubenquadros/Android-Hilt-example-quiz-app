package com.ruben.funed.data

import com.ruben.funed.remote.NetworkManager

/**
 * Created by ruben.quadros on 19/06/21.
 **/
interface DataSource {
    fun api(): NetworkManager
}