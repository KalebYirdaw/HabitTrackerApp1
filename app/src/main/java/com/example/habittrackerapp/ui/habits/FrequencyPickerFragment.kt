package com.example.habittrackerapp.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.R

class FrequencyPickerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_frequency_picker,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.frequencyDaily)
            .setOnClickListener {
                selectFrequency("Daily")
            }

        view.findViewById<View>(R.id.frequencyWeekly)
            .setOnClickListener {
                selectFrequency("Weekly")
            }

        view.findViewById<View>(R.id.frequencyMonthly)
            .setOnClickListener {
                selectFrequency("Monthly")
            }

        view.findViewById<View>(R.id.frequencyCustom)
            .setOnClickListener {
                selectFrequency("Custom")
            }
    }

    private fun selectFrequency(frequency: String) {

        parentFragmentManager.setFragmentResult(
            "frequency_picker_result",
            Bundle().apply {
                putString(
                    "selectedFrequency",
                    frequency
                )
            }
        )

        findNavController().navigateUp()
    }
}