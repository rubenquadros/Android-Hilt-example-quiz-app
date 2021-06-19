package com.ruben.funed.remote

import java.io.IOException

/**
 * Created by ruben.quadros on 19/06/21.
 **/
sealed class RemoteException(message: String): IOException(message) {
    class ClientError(message: String): RemoteException(message)
    class ServerError(message: String): RemoteException(message)
    class NoNetwork(message: String): RemoteException(message)
    class GenericError(message: String): RemoteException(message)
}