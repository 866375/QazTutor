package com.example.qaztutor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.qaztutor.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mActivity: Activity
    private lateinit var mToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mActivity = this

        mAuth = FirebaseAuth.getInstance()

        mToggle =
            ActionBarDrawerToggle(mActivity, mBinding.drawerLayout, R.string.open, R.string.close)
        mBinding.drawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()

        mToggle.isDrawerIndicatorEnabled = false

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);


        mBinding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> Toast.makeText(mActivity, "Home clicked", Toast.LENGTH_SHORT).show()
                R.id.navAccount -> Toast.makeText(mActivity, "Account clicked", Toast.LENGTH_SHORT)
                    .show()
                R.id.navCourses -> Toast.makeText(mActivity, "Courses clicked", Toast.LENGTH_SHORT)
                    .show()
                R.id.navSettings -> Toast.makeText(
                    mActivity,
                    "Settings clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }

        if (mAuth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}