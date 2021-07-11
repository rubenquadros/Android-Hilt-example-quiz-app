package com.ruben.funed.presentation.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ruben.funed.databinding.FragmentConfirmationDialogBinding

class ConfirmationDialogFragment : DialogFragment() {

    private var _binding: FragmentConfirmationDialogBinding? = null
    private val binding get() = _binding!!
    private var listener: ConfirmationListener? = null
    private var title: String? = null
    private var message: String? = null
    private var positiveButton: String? = null
    private var negativeButton: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is ConfirmationListener) {
            listener = parentFragment as ConfirmationListener
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(DIALOG_TITLE)
            message = it.getString(DIALOG_MESSAGE)
            positiveButton = it.getString(DIALOG_POSITIVE_BUTTON)
            negativeButton = it.getString(DIALOG_NEGATIVE_BUTTON)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmationDialogBinding.inflate(inflater, container, false)
        isCancelable = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dialogTitleTv.text = title
        binding.dialogMessageTv.text = message
        binding.dialogPositiveButton.text = positiveButton
        binding.dialogNegativeButton.text = negativeButton
        binding.dialogPositiveButton.setOnClickListener {
            listener?.onPositiveResponse()
            dismiss()
        }
        binding.dialogNegativeButton.setOnClickListener {
            listener?.onNegativeResponse()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val DIALOG_TITLE = "dialog_title"
        private const val DIALOG_MESSAGE = "dialog_message"
        private const val DIALOG_POSITIVE_BUTTON = "positive_button"
        private const val DIALOG_NEGATIVE_BUTTON = "negative_button"

        fun newInstance(
            title: String,
            message: String,
            positiveButton: String,
            negativeButton: String
        ) =
            ConfirmationDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(DIALOG_TITLE, title)
                    putString(DIALOG_MESSAGE, message)
                    putString(DIALOG_POSITIVE_BUTTON, positiveButton)
                    putString(DIALOG_NEGATIVE_BUTTON, negativeButton)
                }
            }
    }

    interface ConfirmationListener {
        fun onPositiveResponse()
        fun onNegativeResponse()
    }
}