package com.ruben.funed.presentation

import android.content.Intent
import android.os.Bundle
import com.ruben.funed.presentation.base.BaseActivity
import com.ruben.funed.presentation.instruction.InstructionsActivity

class SplashActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    startActivity(Intent(this, InstructionsActivity::class.java))
    finish()
  }
}