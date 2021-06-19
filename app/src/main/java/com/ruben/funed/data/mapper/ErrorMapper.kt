package com.ruben.funed.data.mapper

import android.util.Log
import com.ruben.funed.domain.model.ErrorRecord
import com.ruben.funed.domain.model.Record
import com.ruben.funed.domain.model.StatusRecord
import com.ruben.funed.remote.RemoteException

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class ErrorMapper {

    fun <T>mapError(e: RemoteException): Record<T> {
        Log.e(ErrorMapper::class.simpleName, e.message.toString())
        return when (e) {
            is RemoteException.ClientError -> {
                Record(StatusRecord.FAIL, null, ErrorRecord.ClientError)
            }
            is RemoteException.ServerError -> {
                Record(StatusRecord.FAIL, null, ErrorRecord.ServerError)
            }
            is RemoteException.NoNetwork -> {
                Record(StatusRecord.FAIL, null, ErrorRecord.NoNetwork)
            }
            is RemoteException.GenericError -> {
                Record(StatusRecord.FAIL, null, ErrorRecord.GenericError)
            }
        }
    }
}