package com.example.qaztutor.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qaztutor.MainActivity
import com.example.qaztutor.R
import com.example.qaztutor.adapters.UnitAdapter
import com.example.qaztutor.databinding.ActivityLessonsBinding
import com.example.qaztutor.models.TestUnit

class LessonsActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLessonsBinding
    private lateinit var mActivity: Activity
    private lateinit var mToggle: ActionBarDrawerToggle

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLessonsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mActivity = this

        mBinding.toolBar.navHam.setOnClickListener {
            mBinding.drawerLayout.openDrawer(Gravity.START)
        }

        mBinding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    var intent = Intent(mActivity, MainActivity::class.java)
                    intent.putExtra("fragment_id", "home")
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    startActivity(intent)
                    finish()
                }

                R.id.navAccount -> Toast.makeText(mActivity, "Account clicked", Toast.LENGTH_SHORT)
                    .show()
                R.id.navCourses -> {
                    var intent = Intent(mActivity, MainActivity::class.java)
                    intent.putExtra("fragment_id", "courses")
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    startActivity(intent)
                    finish()
                }
                R.id.navSettings -> Toast.makeText(
                    mActivity,
                    "Settings clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }

        val test_unit = ArrayList<TestUnit>()
        test_unit.add(TestUnit("1", "Фонетика"))
        test_unit.add(TestUnit("2", "Буын және оның түрлері"))
        test_unit.add(TestUnit("3", "Фонетикалық талдау"))
        test_unit.add(TestUnit("4", "Тасымал"))
        test_unit.add(TestUnit("5", "Үндестік заңы"))
        test_unit.add(TestUnit("6", "Орфография "))
        val layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        val unitAdapter = UnitAdapter(test_unit)
        mBinding.unitRecyclerView.layoutManager = layoutManager
        mBinding.unitRecyclerView.setHasFixedSize(true)
        mBinding.unitRecyclerView.adapter = unitAdapter

        unitAdapter.onItemClick = {
            var intent = Intent(mActivity, LessonViewActivity::class.java)
            intent.putExtra("unit", it)
            startActivity(intent)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}