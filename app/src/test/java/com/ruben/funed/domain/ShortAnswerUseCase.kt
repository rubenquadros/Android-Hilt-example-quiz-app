package com.ruben.funed.domain

import com.ruben.funed.CoroutinesTestRule
import com.ruben.funed.domain.interactor.UpdateShortAnswerUseCase
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
class ShortAnswerUseCase {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testRepository = mockk<TestRepository>()
    private val updateShortAnswerUseCase = UpdateShortAnswerUseCase(testRepository)

    @Before
    fun init() {
        MockKAnnotations.init(this, true)
    }

    @Test
    fun `should update short answer in database`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        var testData = false
        every { runBlocking { testRepository.updateShortAnswer(any(), any(), any()) } }.answers {
            testData = true
        }

        updateShortAnswerUseCase.invoke(
            coroutinesTestRule.testCoroutineScope,
            coroutinesTestRule.testDispatcher,
            UpdateShortAnswerUseCase.RequestValue("", "", "")
        )
        Assert.assertTrue(testData)
    }
}