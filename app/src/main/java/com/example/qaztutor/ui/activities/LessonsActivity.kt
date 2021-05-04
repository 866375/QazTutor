package com.example.qaztutor.ui.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.qaztutor.databinding.ActivityLessonsBinding
import com.google.firebase.auth.FirebaseAuth

class LessonsActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLessonsBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mActivity: Activity
    private lateinit var mToggle: ActionBarDrawerToggle
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLessonsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mActivity = this

    }
}