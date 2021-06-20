package com.ruben.funed.repository

import com.ruben.funed.domain.repository.TestRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by ruben.quadros on 21/06/21.
 **/
class TestFireAndForget {

    private val testRepository = mockk<TestRepository>()

    @Before
    fun init() {
        MockKAnnotations.init(this, true)
    }

    @Test
    fun `whenever mcq answer is updated repository should communicate same to database`() = runBlocking {
        var testData = false
        every { runBlocking { testRepository.updateMcqAnswer(any(), any()) } }.answers {
           testData = true
        }
        testRepository.updateMcqAnswer("", "")
        Assert.assertTrue(testData)
    }

    @Test
    fun `whenever short answer is updated repository should communicate same to database`() = runBlocking {
        var testData = false
        every { runBlocking { testRepository.updateShortAnswer(any(), any(), any()) } }.answers {
            testData = true
        }
        testRepository.updateShortAnswer("", "", "")
        Assert.assertTrue(testData)
    }
}