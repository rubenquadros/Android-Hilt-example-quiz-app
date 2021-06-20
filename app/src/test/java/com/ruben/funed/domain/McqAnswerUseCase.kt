package com.ruben.funed.domain

import com.ruben.funed.CoroutinesTestRule
import com.ruben.funed.domain.interactor.UpdateMcqAnswerUseCase
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
class McqAnswerUseCase {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testRepository = mockk<TestRepository>()
    private val updateMcqAnswerUseCase = UpdateMcqAnswerUseCase(testRepository)

    @Before
    fun init() {
        MockKAnnotations.init(this, true)
    }

    @Test
    fun `should update mcq answers in database`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        var testData = false
        every { runBlocking { testRepository.updateMcqAnswer(any(), any()) } }.answers {
            testData = true
        }

        updateMcqAnswerUseCase.invoke(
            coroutinesTestRule.testCoroutineScope,
            coroutinesTestRule.testDispatcher,
            UpdateMcqAnswerUseCase.RequestValue("", "")
        )
        Assert.assertTrue(testData)
    }
}