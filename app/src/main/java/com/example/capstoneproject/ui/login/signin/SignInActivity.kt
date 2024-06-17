package com.example.capstoneproject.ui.login.signin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.ui.main.MainActivity
import com.example.capstoneproject.data.tools.ViewModelFactory
import com.example.capstoneproject.databinding.ActivitySignInBinding
import com.example.capstoneproject.ui.login.signup.SignUpActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel by viewModels<SignInViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginButton.setOnClickListener {
            setupAction()
        }
        setupView()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Please fill all the fields")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            showLoading(true)
            viewModel.signIn(email, password)
        }

        viewModel.successMessage.observe(this) { successMessage ->
            if (!successMessage.isNullOrEmpty()) {
                showLoading(false)
                AlertDialog.Builder(this)
                    .setTitle("Success")
                    .setMessage("Anda berhasil login")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                        // Tunggu hingga token tersimpan
                        viewModel.session.observe(this@SignInActivity) { session ->
                            if (session.token.isNotEmpty()) {
                                Log.d(
                                    "SignInActivity",
                                    "Navigating to MainActivity with token: ${session.token}"
                                )
                                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                    .show()
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                showLoading(false)
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Email atau Password salah")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}