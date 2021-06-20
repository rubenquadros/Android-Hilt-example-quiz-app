package com.ruben.funed.data.mapper

import com.ruben.funed.cache.entity.TestEntity
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
}