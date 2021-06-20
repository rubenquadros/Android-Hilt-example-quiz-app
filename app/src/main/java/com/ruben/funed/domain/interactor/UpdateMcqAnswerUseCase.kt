package com.ruben.funed.domain.interactor

import com.ruben.funed.domain.interactor.base.UseCase
import com.ruben.funed.domain.repository.TestRepository
import javax.inject.Inject

/**
 * Created by ruben.quadros on 20/06/21.
 **/
class UpdateMcqAnswerUseCase @Inject constructor(private val testRepository: TestRepository) :
    UseCase<UpdateMcqAnswerUseCase.RequestValue>() {

    override suspend fun run(request: RequestValue) {
        testRepository.updateMcqAnswer(request.id, request.answer)
    }

    data class RequestValue(
        val id: String,
        val answer: String
    )
}