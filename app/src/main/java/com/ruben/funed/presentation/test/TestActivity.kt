package com.ruben.funed.presentation.test

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.ruben.funed.R
import com.ruben.funed.databinding.ActivityTestBinding
import com.ruben.funed.presentation.base.BaseActivity
import com.ruben.funed.presentation.dialogs.InformationDialogFragment
import com.ruben.funed.presentation.submission.SubmissionActivity
import com.ruben.funed.remote.model.Question
import com.ruben.funed.utility.ApplicationConstants
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class TestActivity : BaseActivity(), TestFragment.NavButtonListener, InformationDialogFragment.SubmitListener {

  private lateinit var binding: ActivityTestBinding
  private val testViewModel: TestViewModel by viewModels()
  private lateinit var questions: ArrayList<Question>
  private lateinit var subject: String
  private var duration: Long = 0
  private lateinit var countDownTimer: CountDownTimer
  private lateinit var informationDialogFragment: InformationDialogFragment
  private val ok: String by lazy { getString(R.string.all_ok) }
  private val submitMessage: String by lazy { getString(R.string.time_up_message) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityTestBinding.inflate(layoutInflater)
    setContentView(binding.root)
    readData()
    setupView()
    startTimer()
  }

  private fun readData() {
    val bundle = intent.extras
    bundle?.getParcelableArrayList<Question>(ApplicationConstants.QUESTIONS)?.let {
      questions = it
    }
    bundle?.getString(ApplicationConstants.SUBJECT)?.let {
      subject = it
    }
    bundle?.getLong(ApplicationConstants.DURATION)?.let {
      duration = it
    }
  }

  private fun setupView() {
    setupToolbar(
        binding.testToolbar.toolbar,
        binding.testToolbar.toolbarTitleTv,
        if (this::subject.isInitialized) subject else getString(
            R.string.instructions_toolbar_title),
        false
    )
    binding.testVp.isUserInputEnabled = false
    if (this::questions.isInitialized) {
      val pagerAdapter = TestViewPagerAdapter(this, questions)
      binding.testVp.adapter = pagerAdapter
    }
  }

  private fun startTimer() {
    countDownTimer = object : CountDownTimer(duration * 60 * 1000, 1000) {

      override fun onTick(millisUntilFinished: Long) {
        if (millisUntilFinished <= ApplicationConstants.TWO_MINS) {
          binding.testToolbar.toolbarTimerTv.setTypeface(
              binding.testToolbar.toolbarTimerTv.typeface, Typeface.BOLD)
          binding.testToolbar.toolbarTimerTv.setTextColor(
              ContextCompat.getColor(this@TestActivity, R.color.red_700))
        }
        binding.testToolbar.toolbarTimerTv.text = String.format(ApplicationConstants.TIMER_FORMAT,
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
        )
      }

      override fun onFinish() {
        informationDialogFragment = InformationDialogFragment.newInstance(submitMessage, ok)
        informationDialogFragment.show(supportFragmentManager, ApplicationConstants.INFORMATION_DIALOG_TAG)
      }

    }.start()
  }

  override fun onPrevClicked(
    isNewAnswer: Boolean,
    type: String,
    answer: String,
    answerImage: String,
    id: String
  ) {
    if (isNewAnswer) {
      updateAnswers(type, answer, answerImage, id)
    }
    binding.testVp.setCurrentItem(binding.testVp.currentItem - 1, true)
  }

  override fun onNextClicked(
    isNewAnswer: Boolean,
    type: String,
    answer: String,
    answerImage: String,
    id: String
  ) {
    if (isNewAnswer) {
      updateAnswers(type, answer, answerImage, id)
    }
    binding.testVp.setCurrentItem(binding.testVp.currentItem + 1, true)
  }

  override fun onSubmit(
    isNewAnswer: Boolean,
    type: String,
    answer: String,
    answerImage: String,
    id: String
  ) {
    if (isNewAnswer) {
      updateAnswers(type, answer, answerImage, id)
    }
    navigateToSubmissionScreen()
  }

  private fun updateAnswers(type: String, answer: String, answerImage: String, id: String) {
    if (type == ApplicationConstants.MC) {
      testViewModel.updateMcqAnswer(id, answer)
    } else {
      testViewModel.updateShortAnswer(id, answer, answerImage)
    }
  }

  override fun onSubmit() {
    navigateToSubmissionScreen()
  }

  private fun navigateToSubmissionScreen() {
    startActivity(Intent(this, SubmissionActivity::class.java))
    finish()
  }
}