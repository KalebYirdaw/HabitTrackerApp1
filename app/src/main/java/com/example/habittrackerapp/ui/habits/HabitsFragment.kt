package com.example.habittrackerapp.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R
import com.example.habittrackerapp.api.RetrofitClient
import com.example.habittrackerapp.models.Habit
import com.example.habittrackerapp.models.HabitCompletion
import com.example.habittrackerapp.models.HabitCompletionRequest
import com.example.habittrackerapp.utils.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HabitsFragment : Fragment() {

    private lateinit var habitAdapter: HabitAdapter

    private val habits =
        mutableListOf<Habit>()

    private lateinit var progressBar: ProgressBar

    private lateinit var recyclerView: RecyclerView

    // =============================================================
    // CREATE VIEW
    // =============================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_habits,
            container,
            false
        )
    }

    // =============================================================
    // VIEW CREATED
    // =============================================================

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        recyclerView =
            view.findViewById(
                R.id.rvHabits
            )

        progressBar =
            view.findViewById(
                R.id.progressBar
            )

        setupRecyclerView()

        loadHabits()
    }

    // =============================================================
    // SETUP RECYCLER VIEW
    // =============================================================

    private fun setupRecyclerView() {

        habitAdapter =
            HabitAdapter(

                habits,

                // =================================================
                // EXISTING EDIT HABIT CLICK
                // =================================================

                { habit ->

                    val bundle =
                        Bundle().apply {

                            putString(
                                "editHabitId",
                                habit.id
                            )

                            putString(
                                "habitName",
                                habit.name
                            )

                            putString(
                                "habitDescription",
                                habit.description ?: ""
                            )

                            putString(
                                "habitFrequency",
                                habit.frequency
                            )

                            putString(
                                "habitColor",
                                habit.color
                                    ?: "#90CAF9"
                            )

                            putString(
                                "habitIcon",
                                habit.icon
                                    ?: "⭐"
                            )

                            putInt(
                                "habitGoalValue",
                                habit.goalValue
                            )

                            putString(
                                "habitTaskDays",
                                habit.taskDays
                                    ?: ""
                            )

                            putBoolean(
                                "habitIsActive",
                                habit.isActive
                            )
                        }

                    findNavController().navigate(
                        R.id.action_habits_to_details,
                        bundle
                    )
                },

                // =================================================
                // COMPLETE TODAY CLICK
                // =================================================

                { habit ->

                    completeHabitToday(
                        habit
                    )
                }
            )

        recyclerView.apply {

            layoutManager =
                LinearLayoutManager(
                    requireContext()
                )

            adapter =
                habitAdapter
        }
    }

    // =============================================================
    // LOAD HABITS
    // =============================================================

    private fun loadHabits() {

        val token =
            TokenManager.getToken(
                requireContext()
            )

        if (token.isNullOrBlank()) {

            Toast.makeText(
                requireContext(),
                "Please login first",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        showLoading(true)

        RetrofitClient.apiService
            .getHabits(
                "Bearer $token"
            )
            .enqueue(
                object : Callback<List<Habit>> {

                    override fun onResponse(
                        call: Call<List<Habit>>,
                        response: Response<List<Habit>>
                    ) {

                        showLoading(false)

                        if (response.isSuccessful) {

                            habits.clear()

                            response.body()?.let {
                                habits.addAll(it)
                            }

                            habitAdapter
                                .notifyDataSetChanged()

                        } else if (
                            response.code() == 401
                        ) {

                            Toast.makeText(
                                requireContext(),
                                "Session expired. Please log in again.",
                                Toast.LENGTH_LONG
                            ).show()

                        } else {

                            Toast.makeText(
                                requireContext(),
                                "Failed to load habits",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<List<Habit>>,
                        t: Throwable
                    ) {

                        showLoading(false)

                        Toast.makeText(
                            requireContext(),
                            "Error: ${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
    }

    // =============================================================
    // COMPLETE HABIT TODAY
    // =============================================================

    private fun completeHabitToday(
        habit: Habit
    ) {

        // ---------------------------------------------------------
        // GET TOKEN
        // ---------------------------------------------------------

        val token =
            TokenManager.getToken(
                requireContext()
            )

        if (token.isNullOrBlank()) {

            Toast.makeText(
                requireContext(),
                "Please login first",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // ---------------------------------------------------------
        // GET TODAY'S DATE
        //
        // SimpleDateFormat is used instead of LocalDate because
        // the app supports Android API 25.
        // ---------------------------------------------------------

        val today =
            java.text.SimpleDateFormat(
                "yyyy-MM-dd",
                java.util.Locale.getDefault()
            ).format(
                java.util.Date()
            )

        // ---------------------------------------------------------
        // CREATE COMPLETION REQUEST
        // ---------------------------------------------------------

        val request =
            HabitCompletionRequest(
                date = today,
                completed = true
            )

        // ---------------------------------------------------------
        // SEND COMPLETION TO API
        // ---------------------------------------------------------

        RetrofitClient.apiService
            .completeHabit(
                "Bearer $token",
                habit.id,
                request
            )
            .enqueue(
                object : Callback<HabitCompletion> {

                    override fun onResponse(
                        call: Call<HabitCompletion>,
                        response: Response<HabitCompletion>
                    ) {

                        if (response.isSuccessful) {

                            Toast.makeText(
                                requireContext(),
                                "${habit.name} completed today! ✓",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Reload the habits so that the
                            // latest data is displayed.
                            loadHabits()

                        } else if (
                            response.code() == 401
                        ) {

                            Toast.makeText(
                                requireContext(),
                                "Session expired. Please log in again.",
                                Toast.LENGTH_LONG
                            ).show()

                        } else if (
                            response.code() == 404
                        ) {

                            Toast.makeText(
                                requireContext(),
                                "Habit not found.",
                                Toast.LENGTH_LONG
                            ).show()

                        } else {

                            Toast.makeText(
                                requireContext(),
                                "Failed to complete habit. Code: ${response.code()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<HabitCompletion>,
                        t: Throwable
                    ) {

                        Toast.makeText(
                            requireContext(),
                            "Error: ${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
    }

    // =============================================================
    // LOADING
    // =============================================================

    private fun showLoading(
        show: Boolean
    ) {

        progressBar.visibility =
            if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}