package com.foxxx.messageboardapp.dialogs

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.foxxx.messageboardapp.MainActivity
import com.foxxx.messageboardapp.R
import com.foxxx.messageboardapp.accounthelper.AccountHelper
import com.foxxx.messageboardapp.databinding.SignDialogBinding

class DialogHelper(private val activity: MainActivity) {

    val accHelper = AccountHelper(activity)

    fun createSignInDialog(index: Int) {
        val builder = AlertDialog.Builder(activity)
        val rootDialogElement = SignDialogBinding.inflate(activity.layoutInflater)
        builder.setView(rootDialogElement.root)

        setDialogState(index, rootDialogElement)

        val dialog = builder.create()

        rootDialogElement.btnSignUpIn.setOnClickListener {
            setOnClickSingUpIn(index, rootDialogElement, dialog)
        }
        rootDialogElement.btnForgotPassword.setOnClickListener {
            setOnClickResetPassword(rootDialogElement, dialog)
        }
        rootDialogElement.btnGoogleSignIn.setOnClickListener {
            accHelper.signInWithGoogle()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setOnClickSingUpIn(
        index: Int,
        rootDialogElement: SignDialogBinding,
        dialog: AlertDialog?
    ) {
        dialog?.dismiss()
        if (index == DialogConst.SIGN_UP_STATE) {
            accHelper.signUpWithEmail(
                rootDialogElement.edSignEmail.text.toString(),
                rootDialogElement.edSignPassword.text.toString()
            )
        } else {
            accHelper.signInWithEmail(
                rootDialogElement.edSignEmail.text.toString(),
                rootDialogElement.edSignPassword.text.toString()
            )
        }
    }

    private fun setOnClickResetPassword(
        rootDialogElement: SignDialogBinding,
        dialog: AlertDialog?
    ) {

        if (rootDialogElement.edSignEmail.text.isNotEmpty()) {
            activity.myAuth.sendPasswordResetEmail(rootDialogElement.edSignEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity, R.string.email_reset_password_send, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            dialog?.dismiss()
        } else {
            rootDialogElement.tvDialogMessage.visibility = View.VISIBLE
        }
    }

    private fun setDialogState(index: Int, rootDialogElement: SignDialogBinding) {
        if (index == DialogConst.SIGN_UP_STATE) {
            rootDialogElement.tvSignTitle.text = activity.resources.getString(R.string.sign_up)
            rootDialogElement.btnSignUpIn.text = activity.resources.getString(R.string.sign_up_action)

        } else {
            rootDialogElement.tvSignTitle.text = activity.resources.getString(R.string.sign_in)
            rootDialogElement.btnSignUpIn.text = activity.resources.getString(R.string.sign_in_action)
            rootDialogElement.btnForgotPassword.visibility = View.VISIBLE
        }
    }
}