package com.ruben.funed.viewmodel

import com.ruben.funed.CoroutinesTestRule
import com.ruben.funed.domain.interactor.UpdateMcqAnswerUseCase
import com.ruben.funed.domain.interactor.UpdateShortAnswerUseCase
import com.ruben.funed.presentation.test.TestViewModel
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
class ExamViewModel {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val updateMcqAnswerUseCase = mockk<UpdateMcqAnswerUseCase>()
    private val updateShortAnswerUseCase = mockk<UpdateShortAnswerUseCase>()
    private val viewModel: TestViewModel =
        TestViewModel(updateMcqAnswerUseCase, updateShortAnswerUseCase, coroutinesTestRule.testDispatcher)

    @Before
    fun init() {
        MockKAnnotations.init(this, true)
    }

    @Test
    fun `asking vm to update mcq answer should invoke use case`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        var testData = false
        every { runBlocking { updateMcqAnswerUseCase.invoke(any(), any(), any()) } }.answers {
            testData = true
        }
        viewModel.updateMcqAnswer("", "")
        Assert.assertTrue(testData)
    }

    @Test
    fun `asking vm to update short answer should invoke use case`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        var testData = false
        every { runBlocking { updateShortAnswerUseCase.invoke(any(), any(), any()) } }.answers {
            testData = true
        }
        viewModel.updateShortAnswer("", "", "")
        Assert.assertTrue(testData)
    }

}