package com.example.capstoneproject.ui.login.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.R
import com.example.capstoneproject.data.tools.ViewModelFactory
import com.example.capstoneproject.databinding.ActivitySignUpBinding
import com.example.capstoneproject.ui.login.signin.SignInActivity
import com.example.capstoneproject.ui.tools.DatePickerFragment
import com.google.android.material.textfield.TextInputEditText
import androidx.lifecycle.Observer

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInTextView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.daftarButton.setOnClickListener {
            setupAction()
        }

        viewModel.isLoading.observe(this, Observer { isLoading ->
            showLoading(isLoading)
        })

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                showLoading(false)
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Email sudah digunakan")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        viewModel.successMessage.observe(this) { successMessage ->
            if (!successMessage.isNullOrEmpty()) {
                showLoading(false)
                AlertDialog.Builder(this)
                    .setTitle("Success")
                    .setMessage("Akun berhasil dibuat")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    }
                    .show()
            }
        }

        val dateOfBirthEditText = binding.edLoginDateOfBirth
        dateOfBirthEditText.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupAction() {
        val name = binding.edLoginFullName.text.toString()
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()
        val dateOfBirth = binding.edLoginDateOfBirth.text.toString()
        val gender = binding.genderSpinner.selectedItem.toString()

        if (email.isEmpty() || password.isEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Please fill all the fields")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            viewModel.register(email, password, name, dateOfBirth, gender)
        }
    }

    fun processDatePickerResult(year: Int, month: Int, day: Int) {
        val monthString = (month + 1).toString().padStart(2, '0')
        val dayString = day.toString().padStart(2, '0')
        val dateMessage = "$year/$monthString/$dayString"
        binding.edLoginDateOfBirth.setText(dateMessage)
    }
}