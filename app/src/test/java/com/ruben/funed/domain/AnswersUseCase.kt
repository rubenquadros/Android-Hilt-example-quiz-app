package com.ruben.funed.domain

import com.ruben.funed.CoroutinesTestRule
import com.ruben.funed.domain.interactor.GetAnswersUseCase
import com.ruben.funed.domain.model.ErrorRecord
import com.ruben.funed.domain.model.Record
import com.ruben.funed.domain.model.StatusRecord
import com.ruben.funed.domain.repository.TestRepository
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
 * Created by ruben.quadros on 21/06/21.
 **/
@ExperimentalCoroutinesApi
class AnswersUseCase {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testRepository = mockk<TestRepository>()
    private val getAnswersUseCase = GetAnswersUseCase(testRepository)

    @Before
    fun init() {
        MockKAnnotations.init(this, true)
    }

    @Test
    fun `should get answers entity from database`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        every { runBlocking { testRepository.getAnswers() } } returns
                Record(StatusRecord.SUCCESS, arrayListOf(), null)

        getAnswersUseCase.invoke(
            coroutinesTestRule.testCoroutineScope,
            coroutinesTestRule.testDispatcher,
            GetAnswersUseCase.RequestValue("")
        ) {
            Assert.assertTrue(it.status == StatusRecord.SUCCESS)
            Assert.assertTrue(it.data != null)
            Assert.assertTrue(it.error == null)
        }
    }

    @Test
    fun `should get error entity if no answers in database`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        every { runBlocking { testRepository.getAnswers() } } returns
                Record(StatusRecord.FAIL, arrayListOf(), ErrorRecord.GenericError)

        getAnswersUseCase.invoke(
            coroutinesTestRule.testCoroutineScope,
            coroutinesTestRule.testDispatcher,
            GetAnswersUseCase.RequestValue("")
        ) {
            Assert.assertTrue(it.status == StatusRecord.FAIL)
            Assert.assertTrue(it.data?.size == 0)
            Assert.assertTrue(it.error != null)
        }
    }
}