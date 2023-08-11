package com.foxxx.messageboardapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.foxxx.messageboardapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()

    }
    private fun init() {
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
        }
    }

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.ad_my_ads -> {
                    Log.d("Item", "ad_my_ads")
                    Toast.makeText(this, "Presed ad_my_ads", Toast.LENGTH_LONG).show()
                }

                R.id.ad_car -> {
                    Log.d("Item", "ad_car")
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
//                dialogHelper.createSignDialog(DialogConst.SIGN_IN_STATE)
                }

                R.id.sign_out -> {
//                uiUpdate(null)
//                myAuth.signOut()
                }

                R.id.sign_up -> {
//                dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }
}