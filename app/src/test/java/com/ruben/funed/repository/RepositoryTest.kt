package com.ruben.funed.repository

import com.ruben.funed.data.repository.TestRepositoryImpl
import com.ruben.funed.domain.model.ErrorRecord
import com.ruben.funed.domain.model.StatusRecord
import com.ruben.funed.domain.repository.TestRepository
import com.ruben.funed.repository.fakeimplementation.RepoTestConstants
import com.ruben.funed.repository.fakeimplementation.fail.FakeFailDataSource
import com.ruben.funed.repository.fakeimplementation.success.FakeSuccessDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class RepositoryTest {

    @Test
    fun `should get entity when server provides success response`() = runBlocking {
        val repository: TestRepository = TestRepositoryImpl(FakeSuccessDataSource())
        val result = repository.getTest()
        Assert.assertTrue(result.status == StatusRecord.SUCCESS)
        Assert.assertTrue(result.error == null)
        Assert.assertTrue(result.data?.assesmentId == RepoTestConstants.ASSESSMENT_ID)
    }

    @Test
    fun `should get error entity when server provides fail response`() = runBlocking {
        val repository: TestRepository = TestRepositoryImpl(FakeFailDataSource())
        val result = repository.getTest()
        Assert.assertTrue(result.status == StatusRecord.FAIL)
        Assert.assertTrue(result.data == null)
        Assert.assertTrue(result.error == ErrorRecord.NoNetwork)
    }
}