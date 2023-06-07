package com.example.myanimeapp.ui.register.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myanimeapp.MainActivity
import com.example.myanimeapp.R
import com.example.myanimeapp.databinding.ActivityLoginBinding
import com.example.myanimeapp.models.errors.e_Error
import com.example.myanimeapp.ui.register.signup.SignupActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var model: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this)[LoginViewModel::class.java]

        if(model.isThereCurrentUser()){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            model.successLiveData.observe(this@LoginActivity){
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }

            model.loadingStateLiveData.observe(this@LoginActivity){
                loginLoadingProcessBar.visibility = View.VISIBLE
            }

            model.errorLiveData.observe(this@LoginActivity){
            errorData ->
                loginLoadingProcessBar.visibility = View.GONE
                when(errorData.errorEvent){
                    e_Error.Email -> {
                        loginEmailEditText.error = getString(errorData.errorMessageInt!!)
                    }
                    e_Error.Password -> {
                        loginPasswordEditText.error = getString(errorData.errorMessageInt!!)
                    }
                    e_Error.GenericInt -> {
                        Snackbar.make(loginCoordinatorLayout, errorData.errorMessageInt!!, Toast.LENGTH_SHORT)
                            .setBackgroundTint(resources.getColor(R.color.Red))
                            .setTextColor(resources.getColor(R.color.White))
                            .show()
                    }
                    else -> {
                        Snackbar.make(loginCoordinatorLayout, errorData.errorMessageStr!!, Toast.LENGTH_SHORT)
                            .setBackgroundTint(resources.getColor(R.color.Red))
                            .setTextColor(resources.getColor(R.color.White))
                            .show()
                    }
                }
            }

            loginLoginButton.setOnClickListener{
                val email = loginEmailEditText.text.toString()
                val password = loginPasswordEditText.text.toString()
                model.login(email, password)
            }

            loginSignUpLinkButton.setOnClickListener{
                startActivity(Intent(this@LoginActivity,SignupActivity::class.java))
                finish()
            }
        }
    }
}