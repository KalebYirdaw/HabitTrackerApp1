package com.example.habittrackerapp.ui.habits

import android.graphics.Color
import android.os.Bundle
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.api.RetrofitClient
import com.example.habittrackerapp.models.CreateHabitRequest
import com.example.habittrackerapp.models.Habit
import com.example.habittrackerapp.utils.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HabitDetailsFragment : Fragment() {

    // ------------------------------------------------------------
    // Habit fields
    // ------------------------------------------------------------

    private lateinit var etHabitName: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSaveHabit: Button

    // ------------------------------------------------------------
    // Setting cards
    // ------------------------------------------------------------

    private lateinit var cardColor: View
    private lateinit var cardFrequency: View
    private lateinit var cardGoal: View
    private lateinit var cardDays: View

    // ------------------------------------------------------------
    // Setting display views
    // ------------------------------------------------------------

    private lateinit var colorPreview: View
    private lateinit var tvSelectedColor: TextView
    private lateinit var tvSelectedFrequency: TextView
    private lateinit var tvSelectedGoal: TextView
    private lateinit var tvSelectedDays: TextView

    // ------------------------------------------------------------
    // Selected habit values
    // ------------------------------------------------------------

    private var selectedColor = "#90CAF9"

    private var selectedColorName = "Soft Blue"

    private var selectedFrequency = "Daily"

    private var selectedGoal = 1

    // Default is every day
    private var selectedDays =
        "Mon,Tue,Wed,Thu,Fri,Sat,Sun"

    // ------------------------------------------------------------
    // Create screen
    // ------------------------------------------------------------

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_habit_details,
            container,
            false
        )
    }

    // ------------------------------------------------------------
    // Setup screen
    // ------------------------------------------------------------

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        // --------------------------------------------------------
        // Find habit fields
        // --------------------------------------------------------

        etHabitName =
            view.findViewById(R.id.etHabitName)

        etDescription =
            view.findViewById(R.id.etDescription)

        btnSaveHabit =
            view.findViewById(R.id.btnSaveHabit)

        // --------------------------------------------------------
        // Find setting cards
        // --------------------------------------------------------

        cardColor =
            view.findViewById(R.id.cardColor)

        cardFrequency =
            view.findViewById(R.id.cardFrequency)

        cardGoal =
            view.findViewById(R.id.cardGoal)

        cardDays =
            view.findViewById(R.id.cardDays)

        // --------------------------------------------------------
        // Find setting display views
        // --------------------------------------------------------

        colorPreview =
            view.findViewById(R.id.colorPreview)

        tvSelectedColor =
            view.findViewById(R.id.tvSelectedColor)

        tvSelectedFrequency =
            view.findViewById(R.id.tvSelectedFrequency)

        tvSelectedGoal =
            view.findViewById(R.id.tvSelectedGoal)

        tvSelectedDays =
            view.findViewById(R.id.tvSelectedDays)

        // --------------------------------------------------------
        // Load preset habit name
        // --------------------------------------------------------

        val presetName =
            arguments?.getString("habitName")

        if (!presetName.isNullOrBlank()) {

            etHabitName.setText(presetName)
        }

        // --------------------------------------------------------
        // Set initial values
        // --------------------------------------------------------

        updateColorDisplay()

        updateFrequencyDisplay()

        updateGoalDisplay()

        updateDaysDisplay()

        // --------------------------------------------------------
        // Listen for colour selection
        // --------------------------------------------------------

        parentFragmentManager.setFragmentResultListener(
            "color_picker_result",
            viewLifecycleOwner
        ) { _, bundle ->

            selectedColor =
                bundle.getString("selectedColor")
                    ?: "#90CAF9"

            selectedColorName =
                bundle.getString("selectedColorName")
                    ?: getColorName(selectedColor)

            updateColorDisplay()
        }

        // --------------------------------------------------------
        // Listen for frequency selection
        // --------------------------------------------------------

        parentFragmentManager.setFragmentResultListener(
            "frequency_picker_result",
            viewLifecycleOwner
        ) { _, bundle ->

            selectedFrequency =
                bundle.getString("selectedFrequency")
                    ?: "Daily"

            updateFrequencyDisplay()
        }

        // --------------------------------------------------------
        // Listen for goal selection
        // --------------------------------------------------------

        parentFragmentManager.setFragmentResultListener(
            "goal_picker_result",
            viewLifecycleOwner
        ) { _, bundle ->

            selectedGoal =
                bundle.getInt(
                    "selectedGoal",
                    1
                )

            updateGoalDisplay()
        }

        // --------------------------------------------------------
        // Listen for days selection
        // --------------------------------------------------------

        parentFragmentManager.setFragmentResultListener(
            "days_selection_result",
            viewLifecycleOwner
        ) { _, bundle ->

            selectedDays =
                bundle.getString("selectedDays")
                    ?: "Mon,Tue,Wed,Thu,Fri,Sat,Sun"

            updateDaysDisplay()
        }

        // --------------------------------------------------------
        // Open Colour page
        // --------------------------------------------------------

        cardColor.setOnClickListener {

            findNavController().navigate(
                R.id.action_details_to_color
            )
        }

        // --------------------------------------------------------
        // Open Frequency page
        // --------------------------------------------------------

        cardFrequency.setOnClickListener {

            findNavController().navigate(
                R.id.action_details_to_freq
            )
        }

        // --------------------------------------------------------
        // Open Goal page
        // --------------------------------------------------------

        cardGoal.setOnClickListener {

            findNavController().navigate(
                R.id.action_details_to_goal
            )
        }

        // --------------------------------------------------------
        // Open Days page
        // --------------------------------------------------------

        cardDays.setOnClickListener {

            findNavController().navigate(
                R.id.action_details_to_days
            )
        }

        // --------------------------------------------------------
        // Save habit
        // --------------------------------------------------------

        btnSaveHabit.setOnClickListener {

            createHabit()
        }
    }

    // ============================================================
    // COLOUR DISPLAY
    // ============================================================

    private fun updateColorDisplay() {

        tvSelectedColor.text =
            selectedColorName

        val drawable =
            GradientDrawable()

        drawable.setColor(
            Color.parseColor(selectedColor)
        )

        drawable.cornerRadius = 12f

        colorPreview.background =
            drawable
    }

    // ============================================================
    // FREQUENCY DISPLAY
    // ============================================================

    private fun updateFrequencyDisplay() {

        tvSelectedFrequency.text =
            selectedFrequency
    }

    // ============================================================
    // GOAL DISPLAY
    // ============================================================

    private fun updateGoalDisplay() {

        tvSelectedGoal.text =
            "$selectedGoal time" +
                    if (selectedGoal == 1) {
                        ""
                    } else {
                        "s"
                    }
    }

    // ============================================================
    // DAYS DISPLAY
    // ============================================================

    private fun updateDaysDisplay() {

        // --------------------------------------------------------
        // If every day is selected
        // --------------------------------------------------------

        if (
            selectedDays ==
            "Mon,Tue,Wed,Thu,Fri,Sat,Sun"
        ) {

            tvSelectedDays.text =
                "Every day"

            return
        }

        // --------------------------------------------------------
        // Convert stored abbreviations into readable text
        // --------------------------------------------------------

        val readableDays =
            selectedDays
                .split(",")
                .map { day ->

                    when (day) {

                        "Mon" -> "Mon"

                        "Tue" -> "Tue"

                        "Wed" -> "Wed"

                        "Thu" -> "Thu"

                        "Fri" -> "Fri"

                        "Sat" -> "Sat"

                        "Sun" -> "Sun"

                        else -> day
                    }
                }

        tvSelectedDays.text =
            readableDays.joinToString(", ")
    }

    // ============================================================
    // GET COLOUR NAME
    // ============================================================

    private fun getColorName(
        color: String
    ): String {

        return when (color.uppercase()) {

            "#90CAF9" ->
                "Soft Blue"

            "#81D4FA" ->
                "Sky Blue"

            "#CE93D8" ->
                "Soft Purple"

            "#B39DDB" ->
                "Lavender"

            "#F48FB1" ->
                "Soft Pink"

            "#EF9A9A" ->
                "Soft Red"

            "#FFCC80" ->
                "Soft Orange"

            "#FFF59D" ->
                "Soft Yellow"

            "#A5D6A7" ->
                "Soft Green"

            "#80CBC4" ->
                "Mint"

            "#BCAAA4" ->
                "Soft Brown"

            "#CFD8DC" ->
                "Soft Grey"

            else ->
                "Custom Colour"
        }
    }

    // ============================================================
    // CREATE HABIT
    // ============================================================

    private fun createHabit() {

        // --------------------------------------------------------
        // Get values
        // --------------------------------------------------------

        val name =
            etHabitName.text
                .toString()
                .trim()

        val description =
            etDescription.text
                .toString()
                .trim()

        // --------------------------------------------------------
        // Validate name
        // --------------------------------------------------------

        if (name.isEmpty()) {

            etHabitName.error =
                "Please enter a habit name"

            etHabitName.requestFocus()

            return
        }

        // --------------------------------------------------------
        // Validate goal
        // --------------------------------------------------------

        if (selectedGoal <= 0) {

            Toast.makeText(
                requireContext(),
                "Please select a valid goal",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // --------------------------------------------------------
        // Validate days
        // --------------------------------------------------------

        if (selectedDays.isBlank()) {

            Toast.makeText(
                requireContext(),
                "Please select at least one day",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // --------------------------------------------------------
        // Get authentication token
        // --------------------------------------------------------

        val token =
            TokenManager.getToken(
                requireContext()
            )

        if (token == null) {

            Toast.makeText(
                requireContext(),
                "Please log in first",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        // --------------------------------------------------------
        // Create API request
        // --------------------------------------------------------

        val request =
            CreateHabitRequest(

                name = name,

                description =
                    if (description.isEmpty()) {
                        null
                    } else {
                        description
                    },

                frequency =
                    selectedFrequency,

                color =
                    selectedColor,

                goalValue =
                    selectedGoal,

                // NEW:
                // Send the days selected by the user
                taskDays =
                    selectedDays
            )

        // --------------------------------------------------------
        // Disable button while saving
        // --------------------------------------------------------

        btnSaveHabit.isEnabled =
            false

        btnSaveHabit.text =
            "Saving..."

        // --------------------------------------------------------
        // Send request to API
        // --------------------------------------------------------

        RetrofitClient.apiService
            .createHabit(
                "Bearer $token",
                request
            )
            .enqueue(
                object : Callback<Habit> {

                    override fun onResponse(
                        call: Call<Habit>,
                        response: Response<Habit>
                    ) {

                        // Re-enable button
                        btnSaveHabit.isEnabled =
                            true

                        btnSaveHabit.text =
                            "Save Habit"

                        // ------------------------------------------------
                        // Successful response
                        // ------------------------------------------------

                        if (response.isSuccessful) {

                            Toast.makeText(
                                requireContext(),
                                "Habit created successfully!",
                                Toast.LENGTH_SHORT
                            ).show()

                            findNavController()
                                .navigateUp()

                        } else {

                            Toast.makeText(
                                requireContext(),
                                "Failed to create habit. Code: ${response.code()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    // ----------------------------------------------------
                    // Network/API failure
                    // ----------------------------------------------------

                    override fun onFailure(
                        call: Call<Habit>,
                        t: Throwable
                    ) {

                        btnSaveHabit.isEnabled =
                            true

                        btnSaveHabit.text =
                            "Save Habit"

                        Toast.makeText(
                            requireContext(),
                            "Error: ${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
    }
}