package com.ruben.funed.presentation.instruction

import android.os.Bundle
import com.ruben.funed.databinding.ActivityInstructionsBinding
import com.ruben.funed.presentation.base.BaseActivity

class InstructionsActivity : BaseActivity() {

    private lateinit var binding: ActivityInstructionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}