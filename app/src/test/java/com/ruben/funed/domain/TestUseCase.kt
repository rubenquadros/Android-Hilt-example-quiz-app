package com.ruben.funed.domain

import com.ruben.funed.CoroutinesTestRule
import com.ruben.funed.domain.interactor.GetTestUseCase
import com.ruben.funed.domain.model.ErrorRecord
import com.ruben.funed.domain.model.Record
import com.ruben.funed.domain.model.StatusRecord
import com.ruben.funed.domain.repository.TestRepository
import com.ruben.funed.remote.model.TestResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by ruben.quadros on 19/06/21.
 **/
@ExperimentalCoroutinesApi
class TestUseCase {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testRepository = mockk<TestRepository>()
    private val getTestUseCase = GetTestUseCase(testRepository)

    @Before
    fun init() {
        MockKAnnotations.init(this, true)
    }

    @Test
    fun `should get test entity from repository when server gives success response`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            every { runBlocking { testRepository.getTest() } } returns
                    Record(StatusRecord.SUCCESS, TestResponse(), null)

            getTestUseCase.invoke(
                coroutinesTestRule.testCoroutineScope,
                coroutinesTestRule.testDispatcher,
                GetTestUseCase.RequestValue("")
            ) {
                Assert.assertTrue(it.status == StatusRecord.SUCCESS)
                Assert.assertTrue(it.error == null)
                Assert.assertTrue(it.data != null)
            }
        }

    @Test
    fun `should get error entity from repository when server gives fail response`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            every { runBlocking { testRepository.getTest() } } returns
                    Record(StatusRecord.FAIL, null, ErrorRecord.GenericError)

            getTestUseCase.invoke(
                coroutinesTestRule.testCoroutineScope,
                coroutinesTestRule.testDispatcher,
                GetTestUseCase.RequestValue("")
            ) {
                Assert.assertTrue(it.status == StatusRecord.FAIL)
                Assert.assertTrue(it.error != null)
                Assert.assertTrue(it.data == null)
            }
        }
}