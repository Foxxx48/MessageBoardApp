package com.foxxx.messageboardapp.accounthelper

import android.util.Log
import android.widget.Toast
import com.foxxx.messageboardapp.MainActivity
import com.foxxx.messageboardapp.R
import com.foxxx.messageboardapp.constants.FirebaseAuthConstants
import com.foxxx.messageboardapp.dialogs.GoogleAccConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AccountHelper(private val activity: MainActivity) {

    private lateinit var signInClient: GoogleSignInClient

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    performanceCheck(task, email, password)
                }
        }
    }

    private fun performanceCheck(
        task: Task<AuthResult>,
        email: String,
        password: String
    ) {
        if (task.isSuccessful) {
            sendEmailVerify(task.result?.user!!)
            activity.uiUpdate(task.result?.user)
        } else {

            var exception: Exception
            Log.d("MyLog", "Exception: + ${task.exception}")
            when (task.exception) {
                is FirebaseAuthUserCollisionException -> {
                    exception = task.exception as FirebaseAuthUserCollisionException
                    if (exception.errorCode == FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE) {
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.The_account_already_exists),
                            Toast.LENGTH_SHORT
                        ).show()
                        linkEmailToGoogle(email, password)
                    }
                }
                is FirebaseAuthWeakPasswordException -> {
                    exception = task.exception as FirebaseAuthWeakPasswordException
                    if (exception.errorCode == FirebaseAuthConstants.ERROR_WEAK_PASSWORD) {
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.min_password),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    exception =
                        task.exception as FirebaseAuthInvalidCredentialsException

                    if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL) {
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.wrong_email_format),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.myAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        activity.uiUpdate(task.result?.user)
                    } else {

                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            Log.d("MyLog", "Exception: + ${task.exception}")
                            val exception =
                                task.exception as FirebaseAuthInvalidCredentialsException

                            if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL) {
                                Toast.makeText(
                                    activity,
                                    activity.resources.getString(R.string.wrong_format_or_email),
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            } else {
                                if (exception.errorCode == FirebaseAuthConstants.ERROR_WRONG_PASSWORD) {
                                    Toast.makeText(
                                        activity,
                                        activity.resources.getString(R.string.wrong_password_or_email),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }

                }
        }
    }

    private fun sendEmailVerify(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.send_verify_done),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Log.d("MyLog", "Send Email Exception: + ${task.exception}")
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.send_verify_error),
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(activity, gso)
    }

    fun signInWithGoogle() {
        signInClient = getSignInClient()
        val intent = signInClient.signInIntent
        activity.startActivityForResult(intent, GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    fun singOutG() {
        getSignInClient().signOut()
    }

    fun signInFirebaseWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        activity.myAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Sign In Done", Toast.LENGTH_LONG).show()
                activity.uiUpdate(task.result?.user)
            } else {
                Log.d("MyLog", "Google Sign In Exception: + ${task.exception}")
            }
        }
    }

    private fun linkEmailToGoogle(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        if (activity.myAuth.currentUser != null) {
            activity.myAuth.currentUser?.linkWithCredential(credential)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.link_email_to_google),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                activity,
                activity.resources.getString(R.string.enter_into_acc_or_use_google),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}