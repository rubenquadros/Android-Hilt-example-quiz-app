package com.ruben.funed.presentation.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.ruben.funed.presentation.instruction.InstructionsActivity

/**
 * Created by ruben.quadros on 19/06/21.
 **/
open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler { _, e -> handleUncaughtException(e) }
    }

    fun setupToolbar(toolbar: Toolbar, titleTv: AppCompatTextView, title: String, isBack: Boolean) {
        setSupportActionBar(toolbar)
        titleTv.text = title
        if (isBack) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    fun showProgress(progressBar: ProgressBar, activity: Activity) {
        progressBar.visibility = View.VISIBLE
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun stopProgress(progressBar: ProgressBar, activity: Activity) {
        progressBar.visibility = View.GONE
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun showSnack(msg: String, view: View, action: String) {
        val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(action) {
            snackBar.dismiss()
        }
        snackBar.show()
    }

    private fun handleUncaughtException(e: Throwable) {
        Log.e(this::javaClass.name, e.toString())
        val intent = Intent(this, InstructionsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}