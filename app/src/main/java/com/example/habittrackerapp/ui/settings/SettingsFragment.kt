package com.example.habittrackerapp.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.example.habittrackerapp.R
import com.example.habittrackerapp.ui.auth.LoginActivity
import com.example.habittrackerapp.utils.TokenManager

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userInfo = view.findViewById<TextView>(R.id.tvUserInfo)
        val email = TokenManager.getUserEmail(requireContext())
        userInfo.text = "User: ${email ?: "Not logged in"}"

        view.findViewById<MaterialButton>(R.id.btnLogout)
            .setOnClickListener {
                TokenManager.clearToken(requireContext())
                Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
    }
}
