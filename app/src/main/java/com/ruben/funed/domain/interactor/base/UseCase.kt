package com.ruben.funed.domain.interactor.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by ruben.quadros on 20/06/21.
 **/
abstract class UseCase<Request> {

    abstract suspend fun run(request: Request)

    fun invoke(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        request: Request
    ) {
        scope.launch(dispatcher) { run(request) }
    }
}