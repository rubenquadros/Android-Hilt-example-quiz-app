package com.ruben.funed.presentation.test

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ruben.funed.R
import com.ruben.funed.databinding.FragmentTestBinding
import com.ruben.funed.domain.model.OptionsRecord
import com.ruben.funed.presentation.dialogs.ConfirmationDialogFragment
import com.ruben.funed.remote.model.Question
import com.ruben.funed.utility.ApplicationConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TestFragment : Fragment(), OptionsAdapter.AnswerListener,
    ConfirmationDialogFragment.ConfirmationListener {

    private lateinit var binding: FragmentTestBinding
    private lateinit var confirmationDialogFragment: ConfirmationDialogFragment
    private var listener: NavButtonListener? = null
    private var testData: Question? = null
    private var isFirst: Boolean = false
    private var isLast: Boolean = false
    private var isGallery: Boolean = false
    private var isGranted: Boolean = false
    private var answer = ""
    private var answerPosition = -1

    @Inject lateinit var optionsAdapter: OptionsAdapter

    private val requestPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                if (it.value == false) {
                    isGranted = false
                }
            }
            if (isGranted && !isGallery) {
                //camera
                Log.d("Ruben", "Granted after req camera")
            } else if (isGranted && isGallery) {
                //gallery
                Log.d("Ruben", "Granted after req gallery")
            } else {
                //show snack
                Log.d("Ruben", "denied permission")
            }
        }

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
        if (savedInstanceState?.containsKey(ApplicationConstants.ANSWER_POSITION) == true) {
            this.answerPosition = savedInstanceState.getInt(ApplicationConstants.ANSWER_POSITION)
        }
        binding.qnoTv.text = getString(R.string.test_question_number, testData?.qno.toString())
        binding.marksTv.text = getString(R.string.test_question_marks, testData?.marks.toString())
        binding.questionTv.setDisplayText(testData?.text)
        if (ApplicationConstants.MC == testData?.type) {
            binding.optionsRv.visibility = View.VISIBLE
            binding.saParent.visibility = View.GONE
            testData?.mcOptions?.let {
                val isSelectedList = MutableList(it.size) {false}
                if (answerPosition != -1) {
                    isSelectedList[answerPosition] = true
                }
                optionsAdapter.setItems(OptionsRecord(it, isSelectedList))
            }
            optionsAdapter.setListener(this)
            binding.optionsRv.adapter = optionsAdapter
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
        binding.cameraButton.setOnClickListener {
            handleCameraPermission()
        }
        binding.galleryButton.setOnClickListener {
            handleGalleryPermission()
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

    private fun handleCameraPermission() {
        isGallery = false
        isGranted = true
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) + ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA) || shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d("Ruben", "rationale camera")
                confirmationDialogFragment = ConfirmationDialogFragment.newInstance(
                    getString(R.string.permission_title),
                    getString(R.string.permission_camera),
                    getString(R.string.all_ok),
                    getString(R.string.all_cancel)
                )
                confirmationDialogFragment.show(childFragmentManager, ApplicationConstants.CONFIRMATION_DIALOG_TAG)
            } else {
                Log.d("Ruben", "Req permission camera")
                requestPermission.launch(
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE))
            }
        } else {
            launchCamera()
        }
    }

    private fun handleGalleryPermission() {
        isGallery = true
        isGranted = true
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d("Ruben", "rationale gallery")
                confirmationDialogFragment = ConfirmationDialogFragment.newInstance(
                    getString(R.string.permission_title),
                    getString(R.string.permission_gallery),
                    getString(R.string.all_ok),
                    getString(R.string.all_cancel)
                )
                confirmationDialogFragment.show(childFragmentManager, ApplicationConstants.CONFIRMATION_DIALOG_TAG)
            } else {
                Log.d("Ruben", "Req permission gallery")
                requestPermission.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }
        } else {
            launchGallery()
        }
    }

    private fun launchCamera() {
        Log.d("Ruben", "launch camera")
    }

    private fun launchGallery() {
        Log.d("Ruben", "launch gallery")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ApplicationConstants.ANSWER_POSITION, answerPosition)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        if (this::confirmationDialogFragment.isInitialized && confirmationDialogFragment.isVisible) {
            confirmationDialogFragment.dismiss()
        }
        super.onStop()
    }

    override fun onAnswerSelected(answer: String, position: Int) {
        this.answer = answer
        this.answerPosition = position
        Log.d("Ruben", "$position $answer")
    }

    override fun onPositiveResponse() {
        if (isGallery) {
            launchGallery()
        } else {
            launchCamera()
        }
    }

    override fun onNegativeResponse() {
        //show snack
        Log.d("Ruben", "cancel from dialog")
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