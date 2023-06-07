package com.example.myanimeapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myanimeapp.R
import com.example.myanimeapp.databinding.FragmentProfileBinding
import com.example.myanimeapp.ui.register.login.LoginActivity
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val model =
            ViewModelProvider(this)[ProfileViewModel::class.java]

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        with(binding){
            profileWelcomeTextView.text = getString(R.string.profile_welcome_text, model.getUserEmail())
            profileLogoutButton.setOnClickListener{
                model.logout()
                //TODO: clear persistent Data HERE
                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
        }

        return binding.root
    }
}