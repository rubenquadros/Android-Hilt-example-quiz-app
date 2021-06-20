package com.ruben.funed.data.repository

import com.ruben.funed.cache.DBConstants
import com.ruben.funed.cache.entity.UpdateMcqAnswer
import com.ruben.funed.cache.entity.UpdateShortAnswer
import com.ruben.funed.data.DataSource
import com.ruben.funed.data.mapper.DBMapper
import com.ruben.funed.data.mapper.ErrorMapper
import com.ruben.funed.data.mapper.TestMapper
import com.ruben.funed.domain.model.Record
import com.ruben.funed.domain.repository.TestRepository
import com.ruben.funed.remote.RemoteException
import com.ruben.funed.remote.model.Question
import com.ruben.funed.remote.model.TestResponse
import javax.inject.Inject

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class TestRepositoryImpl @Inject constructor(private val dataSource: DataSource): TestRepository {

    private val testMapper = TestMapper()
    private val errorMapper = ErrorMapper()
    private val dbMapper = DBMapper()

    override suspend fun getTest(): Record<TestResponse> {
        return try {
            dataSource.api().restApi().getTest().run {
                dataSource.database().testDao().deleteAndInsert(dbMapper.mapTestDataEntity(this))
                testMapper.mapTestResponse(this)
            }
        } catch (e: RemoteException) {
            errorMapper.mapError(e)
        }
    }

    override suspend fun updateMcqAnswer(id: String, answer: String) {
        dataSource.database().testDao().updateMcqAnswer(UpdateMcqAnswer(id, answer, DBConstants.UPDATED_STATUS))
    }

    override suspend fun updateShortAnswer(id: String, answer: String, image: String) {
        dataSource.database().testDao().updateShortAnswer(UpdateShortAnswer(id, answer, image, DBConstants.UPDATED_STATUS))
    }
}