package com.example.habittrackerapp.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R
import com.example.habittrackerapp.api.RetrofitClient
import com.example.habittrackerapp.models.Habit
import com.example.habittrackerapp.utils.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HabitsFragment : Fragment() {

    private lateinit var habitAdapter: HabitAdapter
    private val habits = mutableListOf<Habit>()
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvHabits)
        progressBar = view.findViewById(R.id.progressBar)

        setupRecyclerView()
        loadHabits()
    }

    private fun setupRecyclerView() {
        habitAdapter = HabitAdapter(habits) { habit ->
            Toast.makeText(context, "Clicked: ${habit.name}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = habitAdapter
        }
    }

    private fun loadHabits() {
        val token = TokenManager.getToken(requireContext())
        if (token == null) {
            Toast.makeText(context, "Please login first", Toast.LENGTH_SHORT).show()
            return
        }

        showLoading(true)

        RetrofitClient.apiService.getHabits("Bearer $token")
            .enqueue(object : Callback<List<Habit>> {
                override fun onResponse(call: Call<List<Habit>>, response: Response<List<Habit>>) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            habits.clear()
                            habits.addAll(it)
                            habitAdapter.notifyDataSetChanged()
                        }
                    } else {
                        Toast.makeText(context, "Failed to load habits", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Habit>>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}
