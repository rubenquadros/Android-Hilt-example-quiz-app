package com.ruben.funed.viewmodel

import com.ruben.funed.CoroutinesTestRule
import com.ruben.funed.domain.interactor.GetTestUseCase
import com.ruben.funed.presentation.instruction.InstructionsViewModel
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
class InstructionsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getTestUseCase = mockk<GetTestUseCase>()
    private val instructionsViewModel = InstructionsViewModel(getTestUseCase, coroutinesTestRule.testDispatcher)

    @Before
    fun init() {
        MockKAnnotations.init(this, true)
    }

    @Test
    fun `asking vm to get test data should invoke use case to get required data`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
        var testData = false
        every { runBlocking { getTestUseCase.invoke(any(), any(), any(), any()) } }.answers {
            testData = true
        }
        instructionsViewModel.getTestDetails()
        Assert.assertTrue(testData)
        Assert.assertTrue(instructionsViewModel.getTestDetailsResult().value.data != null)
        Assert.assertTrue(instructionsViewModel.getTestDetailsResult().value.error == null)
    }

}