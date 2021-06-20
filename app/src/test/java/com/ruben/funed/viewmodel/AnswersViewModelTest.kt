package com.ruben.funed.viewmodel

import com.ruben.funed.CoroutinesTestRule
import com.ruben.funed.domain.interactor.GetAnswersUseCase
import com.ruben.funed.presentation.submission.SubmissionViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by ruben.quadros on 21/06/21.
 **/
@ExperimentalCoroutinesApi
class AnswersViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getAnswersUseCase = mockk<GetAnswersUseCase>()
    private val submissionViewModel = SubmissionViewModel(getAnswersUseCase, coroutinesTestRule.testDispatcher)

    @Before
    fun init() {
        MockKAnnotations.init(this, true)
    }

    @Test
    fun `asking vm to get answers should invoke use case to get answers from database`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            var testData = false
            every { getAnswersUseCase.invoke(any(), any(), any(), any()) }.answers {
                testData = true
            }
            submissionViewModel.getAnswers()
            Assert.assertTrue(testData)
            Assert.assertTrue(submissionViewModel.getAnswersResult().value.data != null)
            Assert.assertTrue(submissionViewModel.getAnswersResult().value.error == null)
        }
}