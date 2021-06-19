package com.ruben.funed.domain.repository

import com.ruben.funed.domain.model.Record
import com.ruben.funed.remote.model.TestResponse

/**
 * Created by ruben.quadros on 19/06/21.
 **/
interface TestRepository {
    suspend fun getTest(): Record<TestResponse>
}