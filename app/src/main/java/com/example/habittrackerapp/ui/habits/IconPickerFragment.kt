package com.example.habittrackerapp.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.R

class IconPickerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_icon_picker,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.iconRun).setOnClickListener {
            selectIcon("🏃", "Running")
        }

        view.findViewById<View>(R.id.iconWalk).setOnClickListener {
            selectIcon("🚶", "Walking")
        }

        view.findViewById<View>(R.id.iconWorkout).setOnClickListener {
            selectIcon("🏋️", "Workout")
        }

        view.findViewById<View>(R.id.iconStudy).setOnClickListener {
            selectIcon("📚", "Study")
        }

        view.findViewById<View>(R.id.iconWater).setOnClickListener {
            selectIcon("💧", "Drink Water")
        }

        view.findViewById<View>(R.id.iconReading).setOnClickListener {
            selectIcon("📖", "Reading")
        }

        view.findViewById<View>(R.id.iconMeditation).setOnClickListener {
            selectIcon("🧘", "Meditation")
        }

        view.findViewById<View>(R.id.iconSoccer).setOnClickListener {
            selectIcon("⚽", "Soccer")
        }

        view.findViewById<View>(R.id.iconSleep).setOnClickListener {
            selectIcon("😴", "Sleep")
        }
    }

    private fun selectIcon(
        icon: String,
        iconName: String
    ) {
        parentFragmentManager.setFragmentResult(
            "icon_picker_result",
            Bundle().apply {
                putString("selectedIcon", icon)
                putString("selectedIconName", iconName)
            }
        )

        findNavController().navigateUp()
    }
}