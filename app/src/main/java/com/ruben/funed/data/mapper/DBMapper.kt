package com.ruben.funed.data.mapper

import com.ruben.funed.cache.entity.TestEntity
import com.ruben.funed.domain.model.ErrorRecord
import com.ruben.funed.domain.model.Record
import com.ruben.funed.domain.model.StatusRecord
import com.ruben.funed.remote.model.TestResponse

/**
 * Created by ruben.quadros on 20/06/21.
 **/
class DBMapper {

    fun mapTestDataEntity(testResponse: TestResponse): List<TestEntity> {
        val testEntity: ArrayList<TestEntity> = arrayListOf()
        for (i in testResponse.questions.indices) {
            testEntity.add(
                TestEntity(
                    id = testResponse.questions[i].id,
                    qno = testResponse.questions[i].qno,
                    marks = testResponse.questions[i].marks,
                    type = testResponse.questions[i].type,
                    question = testResponse.questions[i].text
                )
            )
        }
        return testEntity
    }

    fun mapTestEntityToRecord(response: List<TestEntity>): Record<List<TestEntity>> {
        return if (response.isEmpty()) {
            Record(StatusRecord.FAIL, response, ErrorRecord.GenericError)
        } else {
            Record(StatusRecord.SUCCESS, response, null)
        }
    }
}