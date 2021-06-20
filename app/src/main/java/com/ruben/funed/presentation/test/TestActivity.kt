package com.ruben.funed.presentation.test

import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import com.ruben.funed.R
import com.ruben.funed.databinding.ActivityTestBinding
import com.ruben.funed.presentation.base.BaseActivity
import com.ruben.funed.remote.model.Question
import com.ruben.funed.utility.ApplicationConstants
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class TestActivity : BaseActivity(), TestFragment.NavButtonListener {

  private lateinit var binding: ActivityTestBinding
  private lateinit var questions: ArrayList<Question>
  private lateinit var subject: String
  private var duration: Long = 0
  private lateinit var countDownTimer: CountDownTimer

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
        //stop test
      }

    }.start()
  }

  override fun onPrevClicked() {
    binding.testVp.setCurrentItem(binding.testVp.currentItem - 1, true)
  }

  override fun onNextClicked() {
    binding.testVp.setCurrentItem(binding.testVp.currentItem + 1, true)
  }

  override fun onSubmit() {
    //do nothing
  }
}