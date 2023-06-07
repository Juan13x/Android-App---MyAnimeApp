package com.example.myanimeapp.ui.register.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myanimeapp.MainActivity
import com.example.myanimeapp.R
import com.example.myanimeapp.databinding.ActivitySignupBinding
import com.example.myanimeapp.models.errors.e_Error
import com.example.myanimeapp.ui.register.login.LoginActivity
import com.google.android.material.snackbar.Snackbar

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var model: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[SignupViewModel::class.java]

        with(binding){
            model.successLiveData.observe(this@SignupActivity){
                startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                finish()
            }

            model.loadingStateLiveData.observe(this@SignupActivity){
                signUpLoadingProcessBar.visibility = View.VISIBLE
            }

            model.errorLiveData.observe(this@SignupActivity){
                    errorData ->
                signUpLoadingProcessBar.visibility = View.GONE
                when(errorData.errorEvent){
                    e_Error.Email -> {
                        signUpEmailEditText.error = getString(errorData.errorMessageInt!!)
                    }
                    e_Error.Password -> {
                        signUpPasswordEditText.error = getString(errorData.errorMessageInt!!)
                    }
                    e_Error.RepPassword -> {
                        signUpRepPasswordEditText.error = getString(errorData.errorMessageInt!!)
                    }
                    e_Error.GenericInt -> {
                        Snackbar.make(signUpCoordinatorLayout, errorData.errorMessageInt!!, Toast.LENGTH_SHORT)
                            .setBackgroundTint(resources.getColor(R.color.Red))
                            .setTextColor(resources.getColor(R.color.White))
                            .show()
                    }
                    else -> {
                        Snackbar.make(signUpCoordinatorLayout, errorData.errorMessageStr!!, Toast.LENGTH_SHORT)
                            .setBackgroundTint(resources.getColor(R.color.Red))
                            .setTextColor(resources.getColor(R.color.White))
                            .show()
                    }
                }
            }
            signUpSignUpButton.setOnClickListener{
                val email = signUpEmailEditText.text.toString()
                val password = signUpPasswordEditText.text.toString()
                val repPassword = signUpRepPasswordEditText.text.toString()
                model.signUp(email, password,repPassword)
            }

            signUpLoginLinkButton.setOnClickListener{
                startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
                finish()
            }
        }
    }
}