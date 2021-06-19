package com.ruben.funed.remote.retrofit

import com.ruben.funed.remote.RemoteException
import com.ruben.funed.remote.ApiConstants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class RetrofitInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val request = chain.request()
            val builder = request.newBuilder()
            val response = chain.proceed(builder.build())
            if (response.code in HttpURLConnection.HTTP_BAD_REQUEST..ApiConstants.STATUS_CODE_499) {
                throw RemoteException.ClientError(response.code)
            } else if (response.code in HttpURLConnection.HTTP_INTERNAL_ERROR..ApiConstants.STATUS_CODE_599) {
                throw RemoteException.ServerError(response.code)
            }
            return response
        } catch (e: Exception) {
            throw when (e) {
                is UnknownHostException -> {
                    RemoteException.NoNetwork()
                }
                is SocketTimeoutException -> {
                    RemoteException.NoNetwork()
                }
                is RemoteException -> {
                    e
                }
                else -> {
                    IOException()
                }
            }
        }
    }
}