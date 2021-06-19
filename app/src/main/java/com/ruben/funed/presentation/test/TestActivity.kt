package com.ruben.funed.presentation.test

import android.os.Bundle
import com.ruben.funed.R
import com.ruben.funed.databinding.ActivityTestBinding
import com.ruben.funed.presentation.base.BaseActivity
import com.ruben.funed.remote.model.Question
import com.ruben.funed.utility.ApplicationConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : BaseActivity(), TestFragment.NavButtonListener {

  private lateinit var binding: ActivityTestBinding
  private lateinit var questions: ArrayList<Question>
  private lateinit var subject: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityTestBinding.inflate(layoutInflater)
    setContentView(binding.root)
    readData()
    setupView()
  }

  private fun readData() {
    val bundle = intent.extras
    bundle?.getParcelableArrayList<Question>(ApplicationConstants.QUESTIONS)?.let {
      questions = it
    }
    bundle?.getString(ApplicationConstants.SUBJECT)?.let {
      subject = it
    }
  }

  private fun setupView() {
    setupToolbar(
        binding.testToolbar.toolbar,
        binding.testToolbar.toolbarTitleTv,
        if(this::subject.isInitialized) subject else getString(R.string.instructions_toolbar_title),
        false
    )
    binding.testVp.isUserInputEnabled = false
    if (this::questions.isInitialized) {
      val pagerAdapter = TestViewPagerAdapter(this, questions)
      binding.testVp.adapter = pagerAdapter
    }
  }

  override fun onPrevClicked() {
    binding.testVp.setCurrentItem(binding.testVp.currentItem-1, true)
  }

  override fun onNextClicked() {
    binding.testVp.setCurrentItem(binding.testVp.currentItem+1, true)
  }

  override fun onSubmit() {
    //do nothing
  }
}