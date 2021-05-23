package com.example.qaztutor.ui.activities

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qaztutor.MainActivity
import com.example.qaztutor.R
import com.example.qaztutor.adapters.UnitAdapter
import com.example.qaztutor.databinding.ActivityLessonsBinding
import com.example.qaztutor.models.Chapter
import com.example.qaztutor.models.User
import com.example.qaztutor.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setUserName()

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

                R.id.navAccount -> {
                    var intent = Intent(mActivity, AccountActivity::class.java)
                    startActivity(intent)
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    finish()
                }
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

        mBinding.backImageView.setOnClickListener { onBackPressed() }

        val chapters_list = ArrayList<Chapter>()
//        test_unit.add(Chapter("1", "Фонетика"))
//        test_unit.add(Chapter("2", "Буын және оның түрлері"))
//        test_unit.add(Chapter("3", "Фонетикалық талдау"))
//        test_unit.add(Chapter("4", "Тасымал"))
//        test_unit.add(Chapter("5", "Үндестік заңы"))
//        test_unit.add(Chapter("6", "Орфография "))

        var lesson_id = intent.getStringExtra("lesson_id")

        GlobalScope.launch(Dispatchers.IO) {
            RetrofitClient.instance.getChapters(lesson_id!!)
                .enqueue(object : Callback<List<Chapter>> {
                    override fun onResponse(
                        call: Call<List<Chapter>>,
                        response: Response<List<Chapter>>
                    ) {
                        if (response.isSuccessful) {
                            var chapters = response.body()
                            for (chapter: Chapter in chapters!!) {
                                chapters_list.add(chapter)
                            }
                            setUpRecyclerView(chapters_list)
                        } else {
                            Toast.makeText(mActivity, response.message(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<Chapter>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        }

    }

    fun setUpRecyclerView(data: List<Chapter>) {
        val layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
        val unitAdapter = UnitAdapter(data)
        mBinding.unitRecyclerView.layoutManager = layoutManager
        mBinding.unitRecyclerView.setHasFixedSize(true)
        mBinding.unitRecyclerView.adapter = unitAdapter

        unitAdapter.onItemClick = {
            var intent = Intent(mActivity, LessonViewActivity::class.java)
            intent.putExtra("chapter", it)
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

    private fun setUserName() {
        FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .get().addOnSuccessListener {
                val user = it.getValue(User::class.java)
                mBinding.toolBar.userNameTextView.setText(user!!.name)
            }
    }

}