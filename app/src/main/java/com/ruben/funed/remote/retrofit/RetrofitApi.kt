package com.ruben.funed.remote.retrofit

import com.ruben.funed.remote.model.TestResponse
import retrofit2.http.GET

/**
 * Created by ruben.quadros on 19/06/21.
 **/
interface RetrofitApi {
    @GET("exam/exam.json")
    suspend fun getTest(): TestResponse
}