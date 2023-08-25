package com.foxxx.messageboardapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.foxxx.messageboardapp.databinding.ActivityMainBinding

import com.foxxx.messageboardapp.dialogs.DialogConst
import com.foxxx.messageboardapp.dialogs.DialogHelper
import com.foxxx.messageboardapp.dialogs.GoogleAccConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var tvAccount: TextView

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val dialogHelper = DialogHelper(this)

    val myAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    dialogHelper.accHelper.signInFirebaseWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.d("MyLog", "API error: ${e.message}")
            }
            /*
                        Log.d("MyLog", "Sign in result")
            */
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.id_new_ads) {
            startActivity(EditAdsActivity.newIntentEditAdsActivity(this))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        setSupportActionBar(binding.mainContent.toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        with(binding) {
            val toggle =
                ActionBarDrawerToggle(
                    this@MainActivity,
                    drawerLayout,
                    mainContent.toolbar,
                    R.string.ac_open,
                    R.string.ac_close
                )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            navView.setNavigationItemSelectedListener(this@MainActivity)

            tvAccount = navView.getHeaderView(0).findViewById(R.id.tvAccountEmail)

        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ad_my_ads -> {

                Toast.makeText(this, "Presed ad_my_ads", Toast.LENGTH_LONG).show()
            }

            R.id.ad_car -> {

                Toast.makeText(this, "Presed ad_car", Toast.LENGTH_LONG).show()
            }

            R.id.ad_pc -> {
                Toast.makeText(this, "Presed ad_pc", Toast.LENGTH_LONG).show()
            }

            R.id.ad_dm -> {
                Toast.makeText(this, "Presed ad_dm", Toast.LENGTH_LONG).show()
            }

            R.id.ad_smart -> {
                Toast.makeText(this, "Presed ad_smart", Toast.LENGTH_LONG).show()
            }

            R.id.sign_in -> {
                dialogHelper.createSignInDialog(DialogConst.SIGN_IN_STATE)
            }

            R.id.sign_out -> {
                uiUpdate(null)
                dialogHelper.accHelper.singOutG()
            }

            R.id.sign_up -> {
                dialogHelper.createSignInDialog(DialogConst.SIGN_UP_STATE)
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun uiUpdate(user: FirebaseUser?) {
        tvAccount.text = if (user == null) {
            resources.getString(R.string.not_reg)
        } else {
            user.email
        }
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(myAuth.currentUser)
    }
}