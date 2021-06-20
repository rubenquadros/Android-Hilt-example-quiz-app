package com.ruben.funed.domain.interactor

import com.ruben.funed.cache.entity.TestEntity
import com.ruben.funed.domain.interactor.base.BaseUseCase
import com.ruben.funed.domain.model.Record
import com.ruben.funed.domain.repository.TestRepository
import javax.inject.Inject

/**
 * Created by ruben.quadros on 20/06/21.
 **/
class GetAnswersUseCase @Inject constructor(private val testRepository: TestRepository) :
    BaseUseCase<GetAnswersUseCase.RequestValue, Record<List<TestEntity>>>() {

    override suspend fun run(request: RequestValue): Record<List<TestEntity>> {
        return testRepository.getAnswers()
    }

    data class RequestValue(
        val request: Any
    )

}