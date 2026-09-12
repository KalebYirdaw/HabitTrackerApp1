package com.example.habittrackerapp.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.habittrackerapp.R
import com.example.habittrackerapp.api.RetrofitClient
import com.example.habittrackerapp.models.Habit
import com.example.habittrackerapp.utils.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadStats()
    }

    private fun loadStats() {
        val token = TokenManager.getToken(requireContext())
        if (token == null) {
            Toast.makeText(context, "Please login first", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitClient.apiService.getHabits("Bearer $token")
            .enqueue(object : Callback<List<Habit>> {
                override fun onResponse(call: Call<List<Habit>>, response: Response<List<Habit>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { habits ->
                            updateStats(habits)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Habit>>, t: Throwable) {
                    Toast.makeText(context, "Error loading stats: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun updateStats(habits: List<Habit>) {
        val total = habits.size
        val active = habits.count { it.isActive }
        val longestStreak = habits.maxOfOrNull { it.longestStreak } ?: 0

        view?.findViewById<TextView>(R.id.tvTotalHabits)?.text = total.toString()
        view?.findViewById<TextView>(R.id.tvActiveHabits)?.text = active.toString()
        view?.findViewById<TextView>(R.id.tvLongestStreak)?.text = "$longestStreak days"
    }
}
