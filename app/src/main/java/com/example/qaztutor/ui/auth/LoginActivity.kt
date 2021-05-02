package com.example.qaztutor.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qaztutor.MainActivity
import com.example.qaztutor.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private lateinit var mActivity: Activity
    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mActivity = this
        mAuth = FirebaseAuth.getInstance()

        mBinding.registerBtn.setOnClickListener {
            val intent = Intent(mActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        mBinding.loginBtn.setOnClickListener {
            val email = mBinding.emailEditText.text.toString().trim()
            val password = mBinding.passwordEditText.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(mActivity, "Email or Password can be empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            //Firebase auth
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(mActivity, "Success!", Toast.LENGTH_SHORT).show()
                    goMainActivity()
                } else {
                    Log.i(TAG, task.exception.toString())
                    Toast.makeText(mActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun goMainActivity() {
        val intent = Intent(mActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}