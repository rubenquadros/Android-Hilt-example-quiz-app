package com.ruben.funed.presentation.test

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ruben.funed.R
import com.ruben.funed.databinding.FragmentTestBinding
import com.ruben.funed.remote.model.Question
import com.ruben.funed.utility.ApplicationConstants

class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding
    private var listener: NavButtonListener? = null
    private var testData: Question? = null
    private var isFirst: Boolean = false
    private var isLast: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavButtonListener) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            testData = it.getParcelable(TEST_DATA)
            isFirst = it.getBoolean(IS_FIRST)
            isLast = it.getBoolean(IS_LAST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.qnoTv.text = getString(R.string.test_question_number, testData?.qno.toString())
        binding.marksTv.text = getString(R.string.test_question_marks, testData?.marks.toString())
        binding.questionTv.setDisplayText(testData?.text)
        if (ApplicationConstants.MC == testData?.type) {
            binding.optionsRv.visibility = View.VISIBLE
            binding.saParent.visibility = View.GONE
        } else {
            binding.optionsRv.visibility = View.GONE
            binding.saParent.visibility = View.VISIBLE
        }
        if (isFirst) {
            binding.prevButton.isEnabled = false
            binding.prevButton.alpha = 0.3F
        } else {
            binding.prevButton.isEnabled = true
            binding.prevButton.alpha = 1F
        }
        if (isLast) {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_done)
            binding.nextButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
            binding.nextButton.text = getString(R.string.all_subimt)
        } else {
            binding.nextButton.text = getString(R.string.all_next)
        }
        binding.prevButton.setOnClickListener {
            listener?.onPrevClicked()
        }
        binding.nextButton.setOnClickListener {
            if (isLast) {
                //show confirmation
            } else {
                listener?.onNextClicked()
            }
        }
    }

    companion object {

        const val TEST_DATA = "test_data"
        const val IS_FIRST = "is_first"
        const val IS_LAST = "is_last"

        fun newInstance(testData: Question, isFirst: Boolean, isLast: Boolean) =
            TestFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TEST_DATA, testData)
                    putBoolean(IS_FIRST, isFirst)
                    putBoolean(IS_LAST, isLast)
                }
            }
    }

    interface NavButtonListener {
        fun onPrevClicked()
        fun onNextClicked()
        fun onSubmit()
    }
}