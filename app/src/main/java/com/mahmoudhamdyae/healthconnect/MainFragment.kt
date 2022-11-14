package com.mahmoudhamdyae.healthconnect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.health.connect.client.HealthConnectClient
import com.mahmoudhamdyae.healthconnect.databinding.FragmentMainBinding

class MainFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        checkAvailability()

        return binding.root
    }

    /** Check if Health Connect is available / installed */
    private fun checkAvailability() {
        when {
            HealthConnectClient.isAvailable(requireContext()) -> {
                // Health Connect is supported and installed.
                val healthConnectClient= HealthConnectClient.getOrCreate(requireContext())
            }
            else -> {
                // Health Connect is not installed.
                installHealthConnect()
            }
        }
    }

    /**
     * Redirect users to Play Store to install Health Connect.
     * Include intent to redirect users to 3P app post-Health Connect onboarding flow.
     */
    private fun installHealthConnect() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.android.vending")
        intent.data = Uri.parse("market://details")
            .buildUpon()
            .appendQueryParameter("id", "com.google.android.apps.healthdata")
            .appendQueryParameter("url", "healthconnect://onboarding")
            .build()
        context?.startActivity(intent)
    }
}