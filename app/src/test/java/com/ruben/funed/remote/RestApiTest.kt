package com.ruben.funed.remote

import com.ruben.funed.remote.model.TestResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.net.HttpURLConnection

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class RestApiTest: BaseTest() {

    @Test
    fun `should be able to get test details when server sends success response`() = runBlocking {
        val expectedResponse = getExpectedResponse("successTestResponse.json", TestResponse::class.java)
        getResponse("successTestResponse.json", HttpURLConnection.HTTP_OK)
        val result = restApi.getTest()
        Assert.assertEquals(expectedResponse.assesmentId, result.assesmentId)
    }

    @Test
    fun `should throw client exception when server sends 4xx response`() {
        Assert.assertThrows(RemoteException.ClientError::class.java) {
            runBlocking {
                getResponse("successTestResponse.json", HttpURLConnection.HTTP_BAD_REQUEST)
                restApi.getTest()
            }
        }
    }

    @Test
    fun `should throw server exception when server sends 5xx response`() {
        Assert.assertThrows(RemoteException.ServerError::class.java) {
            runBlocking {
                getResponse("successTestResponse.json", HttpURLConnection.HTTP_BAD_GATEWAY)
                restApi.getTest()
            }
        }
    }

    @Test
    fun `should throw no network exception in case of time out`() {
        Assert.assertThrows(RemoteException.NoNetwork::class.java) {
            runBlocking {
                getTimeout()
                restApi.getTest()
            }
        }
    }
}