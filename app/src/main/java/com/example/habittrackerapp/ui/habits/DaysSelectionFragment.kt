package com.example.habittrackerapp.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.R

class DaysSelectionFragment : Fragment() {

    private lateinit var checkMonday: CheckBox
    private lateinit var checkTuesday: CheckBox
    private lateinit var checkWednesday: CheckBox
    private lateinit var checkThursday: CheckBox
    private lateinit var checkFriday: CheckBox
    private lateinit var checkSaturday: CheckBox
    private lateinit var checkSunday: CheckBox

    private lateinit var btnSaveDays: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_days_selection,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        checkMonday = view.findViewById(R.id.checkMonday)
        checkTuesday = view.findViewById(R.id.checkTuesday)
        checkWednesday = view.findViewById(R.id.checkWednesday)
        checkThursday = view.findViewById(R.id.checkThursday)
        checkFriday = view.findViewById(R.id.checkFriday)
        checkSaturday = view.findViewById(R.id.checkSaturday)
        checkSunday = view.findViewById(R.id.checkSunday)

        btnSaveDays = view.findViewById(R.id.btnSaveDays)

        setupDayCard(
            view,
            R.id.dayMonday,
            checkMonday
        )

        setupDayCard(
            view,
            R.id.dayTuesday,
            checkTuesday
        )

        setupDayCard(
            view,
            R.id.dayWednesday,
            checkWednesday
        )

        setupDayCard(
            view,
            R.id.dayThursday,
            checkThursday
        )

        setupDayCard(
            view,
            R.id.dayFriday,
            checkFriday
        )

        setupDayCard(
            view,
            R.id.daySaturday,
            checkSaturday
        )

        setupDayCard(
            view,
            R.id.daySunday,
            checkSunday
        )

        btnSaveDays.setOnClickListener {
            saveDays()
        }
    }

    private fun setupDayCard(
        view: View,
        cardId: Int,
        checkBox: CheckBox
    ) {
        view.findViewById<View>(cardId)
            .setOnClickListener {

                checkBox.isChecked =
                    !checkBox.isChecked
            }
    }

    private fun saveDays() {

        val selectedDays = mutableListOf<String>()

        if (checkMonday.isChecked) {
            selectedDays.add("Mon")
        }

        if (checkTuesday.isChecked) {
            selectedDays.add("Tue")
        }

        if (checkWednesday.isChecked) {
            selectedDays.add("Wed")
        }

        if (checkThursday.isChecked) {
            selectedDays.add("Thu")
        }

        if (checkFriday.isChecked) {
            selectedDays.add("Fri")
        }

        if (checkSaturday.isChecked) {
            selectedDays.add("Sat")
        }

        if (checkSunday.isChecked) {
            selectedDays.add("Sun")
        }

        if (selectedDays.isEmpty()) {

            Toast.makeText(
                requireContext(),
                "Please select at least one day",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val daysString =
            selectedDays.joinToString(",")

        parentFragmentManager.setFragmentResult(
            "days_selection_result",
            Bundle().apply {
                putString(
                    "selectedDays",
                    daysString
                )
            }
        )

        findNavController().navigateUp()
    }
}