package com.ruben.funed.domain.interactor

import kotlinx.coroutines.*

/**
 * Created by ruben.quadros on 19/06/21.
 **/
abstract class BaseUseCase<Request, Response> {

    abstract suspend fun run(request: Request): Response

    fun invoke(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        request: Request,
        onResponse: (Response) -> Unit
    ) {
        val job = scope.async(dispatcher) {
            run(request)
        }
        scope.launch(dispatcher) {
            if (isActive) {
                onResponse(job.await())
            }
        }
    }
}