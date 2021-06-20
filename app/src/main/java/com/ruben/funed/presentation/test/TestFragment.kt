package com.ruben.funed.presentation.test

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import com.ruben.funed.BuildConfig
import com.ruben.funed.R
import com.ruben.funed.databinding.FragmentTestBinding
import com.ruben.funed.domain.model.OptionsRecord
import com.ruben.funed.presentation.base.BaseFragment
import com.ruben.funed.presentation.dialogs.ConfirmationDialogFragment
import com.ruben.funed.remote.model.Question
import com.ruben.funed.utility.ApplicationConstants
import com.ruben.funed.utility.ApplicationUtility
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class TestFragment : BaseFragment(), OptionsAdapter.AnswerListener,
    ConfirmationDialogFragment.ConfirmationListener {

    private lateinit var binding: FragmentTestBinding
    private lateinit var confirmationDialogFragment: ConfirmationDialogFragment
    private var listener: NavButtonListener? = null
    private var testData: Question? = null
    private var isFirst: Boolean = false
    private var isLast: Boolean = false
    private var isGallery: Boolean = false
    private var isGranted: Boolean = false
    private var isNewAnswer: Boolean = false
    private var isSubmitTest: Boolean = false
    private var answer = ""
    private var answerPosition = -1
    private lateinit var photoFile: File
    private var photoURI: Uri? = null
    private var answerImageUri: Uri? = null
    private val genericError: String by lazy { getString(R.string.all_generic_error) }
    private val permissionTitle: String by lazy { getString(R.string.permission_title) }
    private val permissionCameraMessage: String by lazy { getString(R.string.permission_camera) }
    private val permissionGalleryMessage: String by lazy { getString(R.string.permission_gallery) }
    private val ok: String by lazy { getString(R.string.all_ok) }
    private val cancel: String by lazy { getString(R.string.all_cancel) }
    private val permissionCameraDenied: String by lazy { getString(R.string.permission_camera_denied) }
    private val permissionGalleryDenied: String by lazy { getString(R.string.permission_gallery_denied) }
    private val submit: String by lazy { getString(R.string.all_subimt) }
    private val submitTitle: String by lazy { getString(R.string.submit_title) }
    private val submitMessage: String by lazy { getString(R.string.submit_message) }

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
                launchCamera()
            } else if (isGranted && isGallery) {
                launchGallery()
            } else {
                showPermissionMessage()
            }
        }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                photoURI?.let {
                    answerImageUri = it
                    cropImage.launch(answerImageUri)
                }
            }
        }

    private val selectPicture =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                answerImageUri = it
                cropImage.launch(answerImageUri)
            }
        }

    private val cropImage = registerForActivityResult(CropImageResultContracts()) { uri ->
        binding.answerIv.visibility = View.VISIBLE
        if (uri != null) {
            answerImageUri = ApplicationUtility.getResizedImage(requireContext(), uri)
            Glide.with(requireContext()).load(answerImageUri).into(binding.answerIv)
        } else {
            answerImageUri?.let {
                Glide.with(requireContext()).load(answerImageUri).into(binding.answerIv)
            }
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
        if (savedInstanceState?.containsKey(ApplicationConstants.ANSWER_TEXT) == true) {
            savedInstanceState.getString(ApplicationConstants.ANSWER_TEXT)?.let {
                this.answer = it
            }
        }
        if (savedInstanceState?.containsKey(ApplicationConstants.ANSWER_IMAGE) == true) {
            savedInstanceState.getString(ApplicationConstants.ANSWER_IMAGE)?.let {
                if (it != ApplicationConstants.NULL_STRING) {
                    this.answerImageUri = Uri.parse(it)
                }
            }
        }
        savedInstanceState?.clear()
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
            binding.shortAnswerEt.doOnTextChanged { text, _, _, _ ->
                isNewAnswer = true
                this.answer = text.toString()
            }
            answerImageUri?.let {
                binding.answerIv.visibility = View.VISIBLE
                Glide.with(requireContext()).load(it).into(binding.answerIv)
            }
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
            binding.nextButton.text = submit
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
            listener?.onPrevClicked(
                isNewAnswer,
                testData?.type.toString(),
                answer,
                answerImageUri.toString(),
                testData?.id.toString())
        }
        binding.nextButton.setOnClickListener {
            if (isLast) {
                isSubmitTest = true
                confirmationDialogFragment = ConfirmationDialogFragment.newInstance(
                    submitTitle,
                    submitMessage,
                    submit,
                    cancel
                )
                confirmationDialogFragment.show(
                    childFragmentManager,
                    ApplicationConstants.CONFIRMATION_DIALOG_TAG
                )
            } else {
                listener?.onNextClicked(
                    isNewAnswer,
                    testData?.type.toString(),
                    answer,
                    answerImageUri.toString(),
                    testData?.id.toString()
                )
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
                confirmationDialogFragment = ConfirmationDialogFragment.newInstance(
                    permissionTitle,
                    permissionCameraMessage,
                    ok,
                    cancel
                )
                confirmationDialogFragment.show(childFragmentManager, ApplicationConstants.CONFIRMATION_DIALOG_TAG)
            } else {
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
                confirmationDialogFragment = ConfirmationDialogFragment.newInstance(
                    permissionTitle,
                    permissionGalleryMessage,
                    ok,
                    cancel
                )
                confirmationDialogFragment.show(childFragmentManager, ApplicationConstants.CONFIRMATION_DIALOG_TAG)
            } else {
                requestPermission.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }
        } else {
            launchGallery()
        }
    }

    private fun launchCamera() {
        try {
            photoFile = ApplicationUtility.createImageFile(requireContext())
        } catch (ex: IOException) {
            showSnack(genericError, binding.parent, ok)
            return
        }
        photoURI =
            FileProvider.getUriForFile(
                requireContext(),
                BuildConfig.APPLICATION_ID + ApplicationConstants.PROVIDER,
                photoFile
            )
        takePicture.launch(photoURI)
    }

    private fun launchGallery() {
        selectPicture.launch(ApplicationConstants.PICK_IMAGES)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ApplicationConstants.ANSWER_POSITION, answerPosition)
        outState.putString(ApplicationConstants.ANSWER_TEXT, answer)
        outState.putString(ApplicationConstants.ANSWER_IMAGE, answerImageUri.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        if (this::confirmationDialogFragment.isInitialized && confirmationDialogFragment.isVisible) {
            confirmationDialogFragment.dismiss()
        }
        super.onStop()
    }

    override fun onAnswerSelected(answer: String, position: Int) {
        this.isNewAnswer = true
        this.answer = answer
        this.answerPosition = position
    }

    override fun onPositiveResponse() {
        if (isSubmitTest) {
            listener?.onSubmit(
                isNewAnswer,
                testData?.type.toString(),
                answer,
                answerImageUri.toString(),
                testData?.id.toString()
            )
        } else {
            if (isGallery) {
                handleGalleryPermission()
            } else {
                handleCameraPermission()
            }
        }
    }

    override fun onNegativeResponse() {
        if (!isSubmitTest) {
            showPermissionMessage()
        } else {
            isSubmitTest = false
        }
    }

    private fun showPermissionMessage() {
        val message = if (isGallery) {
            permissionGalleryDenied
        } else {
            permissionCameraDenied
        }
        showSnack(message, binding.parent, ok)
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
        fun onPrevClicked(
            isNewAnswer: Boolean,
            type: String,
            answer: String,
            answerImage: String,
            id: String
        )

        fun onNextClicked(
            isNewAnswer: Boolean,
            type: String,
            answer: String,
            answerImage: String,
            id: String
        )

        fun onSubmit(
            isNewAnswer: Boolean,
            type: String,
            answer: String,
            answerImage: String,
            id: String
        )
    }
}