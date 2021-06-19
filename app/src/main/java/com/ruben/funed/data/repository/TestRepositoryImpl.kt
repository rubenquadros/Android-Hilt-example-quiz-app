package com.ruben.funed.data.repository

import com.ruben.funed.data.DataSource
import com.ruben.funed.data.mapper.ErrorMapper
import com.ruben.funed.data.mapper.TestMapper
import com.ruben.funed.domain.model.Record
import com.ruben.funed.domain.repository.TestRepository
import com.ruben.funed.remote.RemoteException
import com.ruben.funed.remote.model.TestResponse
import javax.inject.Inject

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class TestRepositoryImpl @Inject constructor(private val dataSource: DataSource): TestRepository {

    private val testMapper = TestMapper()
    private val errorMapper = ErrorMapper()

    override suspend fun getTest(): Record<TestResponse> {
        return try {
            dataSource.api().restApi().getTest().run {
                testMapper.mapTestResponse(this)
            }
        } catch (e: RemoteException) {
            errorMapper.mapError(e)
        }
    }
}