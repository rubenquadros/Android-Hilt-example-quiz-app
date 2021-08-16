package com.ruben.funed.presentation.submission

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ruben.funed.R
import com.ruben.funed.cache.entity.TestEntity
import com.ruben.funed.databinding.ActivitySubmissionBinding
import com.ruben.funed.domain.model.ErrorRecord
import com.ruben.funed.domain.model.StatusRecord
import com.ruben.funed.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SubmissionActivity : BaseActivity() {

    private lateinit var binding: ActivitySubmissionBinding
    private val submissionViewModel: SubmissionViewModel by viewModels()

    @Inject lateinit var answersAdapter: AnswersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubmissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        observeData()
        getAnswers()
    }

    private fun setupView() {
        setupToolbar(
            binding.submissionToolbar.toolbar,
            binding.submissionToolbar.toolbarTitleTv,
            getString(R.string.submission_title),
            false
        )
    }

    private fun observeData() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                submissionViewModel.getAnswersResult().collect { record ->
                    when (record.status) {
                        StatusRecord.LOADING -> {
                            showProgress(binding.submissionsPb, this@SubmissionActivity)
                        }
                        StatusRecord.SUCCESS -> {
                            record.data?.let { showAnswers(it) }
                        }
                        StatusRecord.FAIL -> {
                            record.error?.let { handleError(it) }
                        }
                    }
                }
            }
        }
    }

    private fun showAnswers(data: List<TestEntity>) {
        stopProgress(binding.submissionsPb, this)
        answersAdapter.setItems(data)
        binding.submissionRv.adapter = answersAdapter
    }

    private fun getAnswers() {
        submissionViewModel.getAnswers()
    }

    private fun handleError(e: ErrorRecord) {
        stopProgress(binding.submissionsPb, this)
        binding.submissionRv.visibility = View.GONE
        binding.errorTv.visibility = View.VISIBLE
    }
}