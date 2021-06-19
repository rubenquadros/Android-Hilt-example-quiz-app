package com.ruben.funed.presentation.instruction

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ruben.funed.R
import com.ruben.funed.databinding.ActivityInstructionsBinding
import com.ruben.funed.domain.model.ErrorRecord
import com.ruben.funed.domain.model.StatusRecord
import com.ruben.funed.presentation.base.BaseActivity
import com.ruben.funed.remote.model.TestResponse
import com.ruben.funed.utility.ApplicationConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class InstructionsActivity : BaseActivity() {

    private lateinit var binding: ActivityInstructionsBinding
    private val instructionsViewModel: InstructionsViewModel by viewModels()
    private lateinit var countDownTimer: CountDownTimer
    private var isActive = false
    private val ok: String by lazy { getString(R.string.all_ok) }
    private val noNetwork: String by lazy { getString(R.string.all_no_network) }
    private val genericError: String by lazy { getString(R.string.all_generic_error) }
    private val tryAgain: String by lazy { getString(R.string.all_try_again) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        observeData()
        getTestDetails()
    }

    private fun setupView() {
        setupToolbar(
            binding.instructionsToolbar.toolbar,
            binding.instructionsToolbar.toolbarTitleTv,
            getString(R.string.instructions_toolbar_title),
            false
        )
        binding.retryButton.setOnClickListener {
            showProgress(binding.instructionsPb, this)
            instructionsViewModel.getTestDetails()
        }
        binding.enterTestButton.setOnClickListener {
            Log.d("Ruben", "Enter test")
        }
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                instructionsViewModel.getTestDetailsResult().collect { record ->
                    when (record.status) {
                        StatusRecord.LOADING -> {
                            showProgress(binding.instructionsPb, this@InstructionsActivity)
                        }
                        StatusRecord.SUCCESS -> {
                            record.data?.let {
                                showTestDetails(it)
                            }
                        }
                        StatusRecord.FAIL -> {
                            record.error?.let {
                                handleError(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getTestDetails() {
        instructionsViewModel.getTestDetails()
    }

    private fun showTestDetails(data: TestResponse) {
        stopProgress(binding.instructionsPb, this)
        binding.retryButton.visibility = View.GONE
        binding.instructionsToolbar.toolbarTitleTv.text = data.assessmentName
        binding.subjectTv.text = data.subject
        binding.middleParent.visibility = View.VISIBLE
        binding.marksTv.text = getString(R.string.total_marks, data.totalMarks.toString())
        binding.durationTv.text = getString(R.string.test_duration, data.duration.toString())
        binding.instructionsParent.visibility = View.VISIBLE
        if (!isActive) {
            startTimer()
        }
        binding.enterTestButton.visibility = View.VISIBLE
    }

    private fun startTimer() {
        isActive = true
        binding.instructionsTimer.visibility = View.VISIBLE
        countDownTimer = object : CountDownTimer(
            ApplicationConstants.INSTRUCTIONS_TIMER.toLong(),
            ApplicationConstants.TIMER_INTERVAL
        ) {

            override fun onTick(millisUntilFinished: Long) {
                binding.instructionsTimer.text = String.format(
                    ApplicationConstants.TIMER_FORMAT,
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
                )
            }

            override fun onFinish() {
                binding.enterTestButton.alpha = 1F
                binding.enterTestButton.isEnabled = true
                binding.instructionsTimer.visibility = View.GONE
            }
        }.start()
    }

    private fun handleError(error: ErrorRecord) {
        stopProgress(binding.instructionsPb, this)
        when (error) {
            is ErrorRecord.NoNetwork -> {
                showSnack(noNetwork, binding.parent, ok)
            }
            else -> {
                showSnack(genericError, binding.parent, ok)
            }
        }
        binding.subjectTv.text = tryAgain
        binding.retryButton.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        if (this::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
        super.onDestroy()
    }
}