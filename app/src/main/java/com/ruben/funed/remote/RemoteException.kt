package com.ruben.funed.remote

import java.io.IOException

/**
 * Created by ruben.quadros on 19/06/21.
 **/
sealed class RemoteException: IOException() {
    class ClientError(code: Int): RemoteException()
    class ServerError(code: Int): RemoteException()
    class NoNetwork(): RemoteException()
}