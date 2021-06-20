package com.ruben.funed.presentation.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ruben.funed.databinding.FragmentInformationDialogBinding

class InformationDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentInformationDialogBinding
    private var listener: SubmitListener? = null
    private var message: String? = null
    private var button: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SubmitListener) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            message = it.getString(DIALOG_MESSAGE)
            button = it.getString(DIALOG_BUTTON)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformationDialogBinding.inflate(inflater, container, false)
        isCancelable = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.messageTv.text = message
        binding.button.text = button
        binding.button.setOnClickListener {
            listener?.onSubmit()
            dismiss()
        }
    }

    companion object {

        private const val DIALOG_MESSAGE = "dialog_message"
        private const val DIALOG_BUTTON = "dialog_button"

        fun newInstance(message: String, button: String) =
            InformationDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(DIALOG_MESSAGE, message)
                    putString(DIALOG_BUTTON, button)
                }
            }
    }

    interface SubmitListener {
        fun onSubmit()
    }
}