package com.ruben.funed.domain.interactor

import com.ruben.funed.domain.interactor.base.BaseUseCase
import com.ruben.funed.domain.model.Record
import com.ruben.funed.domain.repository.TestRepository
import com.ruben.funed.remote.model.TestResponse
import javax.inject.Inject

/**
 * Created by ruben.quadros on 19/06/21.
 **/
class GetTestUseCase @Inject constructor(private val testRepository: TestRepository) :
    BaseUseCase<GetTestUseCase.RequestValue, Record<TestResponse>>() {

    data class RequestValue(
        val request: Any
    )

    override suspend fun run(request: RequestValue): Record<TestResponse> {
        return testRepository.getTest()
    }
}