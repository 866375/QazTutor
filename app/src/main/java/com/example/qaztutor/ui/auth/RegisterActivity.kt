package com.example.qaztutor.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qaztutor.MainActivity
import com.example.qaztutor.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private val TAG = "RegisterActivity"
    private lateinit var mActivity: Activity
    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mActivity = this
        mAuth = FirebaseAuth.getInstance()

        mBinding.loginBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(mActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        mBinding.registerBtn.setOnClickListener {
            val email = mBinding.emailEditText.text.toString().trim()
            val name = mBinding.nameEditText.text.toString().trim()
            val password = mBinding.passwordEditText.text.toString().trim()
            val repeatPassword = mBinding.repeatPasswordEditText.text.toString().trim()

            if (email.isBlank() || name.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
                Toast.makeText(mActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!password.equals(repeatPassword)) {
                Toast.makeText(mActivity, "Passwords not matching", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    goMainActivity()
                } else {
                    Log.i(TAG, task.exception.toString())
                    Toast.makeText(mActivity, "Registration failed from here", Toast.LENGTH_SHORT).show()
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