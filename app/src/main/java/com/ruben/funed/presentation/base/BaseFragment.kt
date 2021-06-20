package com.ruben.funed.presentation.base

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Created by ruben.quadros on 20/06/21.
 **/
open class BaseFragment: Fragment() {

    fun showSnack(msg: String, view: View, action: String) {
        val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(action) {
            snackBar.dismiss()
        }
        snackBar.show()
    }
}