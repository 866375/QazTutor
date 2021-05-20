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
import com.example.qaztutor.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private val TAG = "RegisterActivity"
    private lateinit var mActivity: Activity
    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

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
            mBinding.progressBar.visibility = View.VISIBLE
            register()

        }

    }

    private fun register() {
        val email = mBinding.emailEditText.text.toString().trim()
        val name = mBinding.nameEditText.text.toString().trim()
        val password = mBinding.passwordEditText.text.toString().trim()
        val repeatPassword = mBinding.repeatPasswordEditText.text.toString().trim()

        if (email.isBlank() || name.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
            Toast.makeText(mActivity, "Registration failed", Toast.LENGTH_SHORT).show()
            mBinding.progressBar.visibility = View.GONE
            return
        }

        if (!password.equals(repeatPassword)) {
            Toast.makeText(mActivity, "Passwords not matching", Toast.LENGTH_SHORT).show()
            mBinding.progressBar.visibility = View.GONE
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mBinding.progressBar.visibility = View.GONE
                val user = User(email, name, password)
                FirebaseDatabase.getInstance().getReference("Users")
                    .child(mAuth.currentUser!!.uid)
                    .setValue(user).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(mActivity, "Ready", Toast.LENGTH_SHORT).show()
                            mBinding.progressBar.visibility = View.GONE
                            goMainActivity()
                        } else {
                            Log.i(TAG, task.exception.toString())
                            mBinding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                mActivity,
                                "Registration failed from here",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    }
            } else {
                Log.i(TAG, task.exception.toString())
                mBinding.progressBar.visibility = View.GONE
                Toast.makeText(mActivity, "Registration failed from here", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun goMainActivity() {
        val intent = Intent(mActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}