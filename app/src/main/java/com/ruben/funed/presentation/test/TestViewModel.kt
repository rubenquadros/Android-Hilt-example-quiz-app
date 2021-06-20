package com.ruben.funed.presentation.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.funed.domain.interactor.UpdateMcqAnswerUseCase
import com.ruben.funed.domain.interactor.UpdateShortAnswerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by ruben.quadros on 20/06/21.
 **/
@HiltViewModel
class TestViewModel @Inject constructor(
    private val updateMcqAnswerUseCase: UpdateMcqAnswerUseCase,
    private val updateShortAnswerUseCase: UpdateShortAnswerUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    fun updateMcqAnswer(id: String, answer: String) {
        updateMcqAnswerUseCase.invoke(
            viewModelScope,
            dispatcher,
            UpdateMcqAnswerUseCase.RequestValue(id, answer)
        )
    }

    fun updateShortAnswer(id: String, answer: String, image: String) {
        updateShortAnswerUseCase.invoke(
            viewModelScope,
            dispatcher,
            UpdateShortAnswerUseCase.RequestValue(id, answer, image)
        )
    }
}