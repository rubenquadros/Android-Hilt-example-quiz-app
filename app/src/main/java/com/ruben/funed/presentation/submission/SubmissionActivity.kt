package com.ruben.funed.presentation.submission

import android.os.Bundle
import com.ruben.funed.databinding.ActivitySubmissionBinding
import com.ruben.funed.presentation.base.BaseActivity

class SubmissionActivity : BaseActivity() {

    private lateinit var binding: ActivitySubmissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubmissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}