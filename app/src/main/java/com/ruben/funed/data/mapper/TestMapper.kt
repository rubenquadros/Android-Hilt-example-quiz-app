package com.ruben.funed.data.mapper

import com.ruben.funed.domain.model.Record
import com.ruben.funed.domain.model.StatusRecord
import com.ruben.funed.remote.model.TestResponse

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class TestMapper {

    fun mapTestResponse(testResponse: TestResponse): Record<TestResponse> {
        return Record(StatusRecord.SUCCESS, testResponse, null)
    }
}