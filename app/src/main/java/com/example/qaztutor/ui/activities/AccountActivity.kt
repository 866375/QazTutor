package com.example.qaztutor.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.qaztutor.MainActivity
import com.example.qaztutor.R
import com.example.qaztutor.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAccountBinding
    private lateinit var mActivity: Activity
    private lateinit var mToggle: ActionBarDrawerToggle
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mActivity = this

        mBinding.toolBar.navHam.setOnClickListener {
            mBinding.drawerLayout.openDrawer(Gravity.START)
        }

        mBinding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    finishAffinity()
                    var intent = Intent(mActivity, MainActivity::class.java)
                    intent.putExtra("fragment_id", "home")
                    startActivity(intent)
                }

                R.id.navAccount -> {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.navCourses -> {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    finishAffinity()
                    var intent = Intent(mActivity, MainActivity::class.java)
                    intent.putExtra("fragment_id", "courses")
                    startActivity(intent)
                }
                R.id.navSettings -> Toast.makeText(
                    mActivity,
                    "Settings clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }
    }
}