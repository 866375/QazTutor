package com.example.qaztutor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
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
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mActivity = this

        mAuth = FirebaseAuth.getInstance()



        mBinding.navHam.setOnClickListener {
            Toast.makeText(mActivity, "Clicked", Toast.LENGTH_SHORT).show()
            mBinding.drawerLayout.openDrawer(Gravity.START)
        }

        mBinding.testBtn.setOnClickListener {
            Toast.makeText(mActivity, "Clicked", Toast.LENGTH_SHORT).show()
        }

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
                R.id.navLogout -> logout()
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

    private fun logout() {
        mAuth.signOut()
        val intent = Intent(mActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}