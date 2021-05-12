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
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.qaztutor.databinding.ActivityMainBinding
import com.example.qaztutor.ui.activities.AccountActivity
import com.example.qaztutor.ui.auth.LoginActivity
import com.example.qaztutor.ui.fragments.CoursesFragment
import com.example.qaztutor.ui.fragments.HomeFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mActivity: Activity
    private lateinit var mToggle: ActionBarDrawerToggle
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mFragment: Fragment

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mActivity = this

        mAuth = FirebaseAuth.getInstance()
        checkUser()

        mBinding.toolBar.navHam.setOnClickListener {
            mBinding.drawerLayout.openDrawer(Gravity.START)
        }

        if (intent.getStringExtra("fragment_id") == "home") {
            mFragment = HomeFragment()
            addFragmentToActivity(mFragment)
        } else if (intent.getStringExtra("fragment_id") == "courses") {
            mFragment = CoursesFragment()
            addFragmentToActivity(mFragment)
        } else {
            mFragment = HomeFragment()
            addFragmentToActivity(mFragment)
        }


        mBinding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    if (mFragment.equals(HomeFragment)) {
                        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    } else {
                        mFragment = HomeFragment()
                        addFragmentToActivity(mFragment)
                        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                }

                R.id.navAccount -> {
                    var intent = Intent(mActivity, AccountActivity::class.java)
                    startActivity(intent)
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.navCourses -> {
                    if (mFragment.equals(CoursesFragment)) {
                        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    } else {
                        mFragment = CoursesFragment()
                        addFragmentToActivity(mFragment)
                        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                }
                R.id.navSettings -> Toast.makeText(
                    mActivity,
                    "Settings clicked",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.navLogout -> logout()
            }
            true
        }


        mBinding.mainProgressBar.setProgress(56)
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

    private fun checkUser() {
        if (mAuth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun addFragmentToActivity(fragment: Fragment?) {
        if (fragment == null) return
        val fm = supportFragmentManager
        val tr = fm.beginTransaction()
        tr.replace(R.id.frameLayout, fragment)
        tr.commitAllowingStateLoss()
        mFragment = fragment
    }

}