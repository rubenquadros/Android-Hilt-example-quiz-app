package com.ruben.funed.presentation.submission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.funed.cache.entity.TestEntity
import com.ruben.funed.domain.interactor.GetAnswersUseCase
import com.ruben.funed.domain.model.Record
import com.ruben.funed.domain.model.StatusRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by ruben.quadros on 20/06/21.
 **/
@HiltViewModel
class SubmissionViewModel @Inject constructor(
    private val getAnswersUseCase: GetAnswersUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val answerResult: MutableStateFlow<Record<List<TestEntity>>> =
        MutableStateFlow(Record(StatusRecord.LOADING, arrayListOf(), null))

    fun getAnswers() {
        getAnswersUseCase.invoke(
            viewModelScope,
            dispatcher,
            GetAnswersUseCase.RequestValue("")
        ) { handleAnswersResult(it) }
    }

    private fun handleAnswersResult(record: Record<List<TestEntity>>) {
        answerResult.value = record
    }

    fun getAnswersResult(): StateFlow<Record<List<TestEntity>>> {
        return answerResult
    }
}