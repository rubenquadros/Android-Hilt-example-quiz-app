package com.ruben.funed.presentation.instruction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.funed.domain.interactor.GetTestUseCase
import com.ruben.funed.domain.model.Record
import com.ruben.funed.domain.model.StatusRecord
import com.ruben.funed.remote.model.TestResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 19/06/21.
 **/
@HiltViewModel
class InstructionsViewModel @Inject constructor(
    private val getTestUseCase: GetTestUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) :
    ViewModel() {

    private val testResult: MutableStateFlow<Record<TestResponse>> =
        MutableStateFlow(Record(StatusRecord.LOADING, TestResponse(), null))

    fun getTestDetails() {
        testResult.value = Record(StatusRecord.LOADING, TestResponse(), null)
        getTestUseCase.invoke(
            viewModelScope,
            dispatcher,
            GetTestUseCase.RequestValue("")
        ) { handleTestResponse(it) }
    }

    private fun handleTestResponse(record: Record<TestResponse>) {
        testResult.value = record
    }

    fun getTestDetailsResult(): StateFlow<Record<TestResponse>> {
        return testResult
    }
}