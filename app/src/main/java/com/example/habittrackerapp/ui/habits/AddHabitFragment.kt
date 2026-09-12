package com.example.habittrackerapp.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

class AddHabitFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var spinnerFrequency: Spinner
    private lateinit var btnSave: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_habit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etName = view.findViewById(R.id.etHabitName)
        etDescription = view.findViewById(R.id.etHabitDescription)
        spinnerFrequency = view.findViewById(R.id.spinnerFrequency)
        btnSave = view.findViewById(R.id.btnSaveHabit)
        progressBar = view.findViewById(R.id.progressBar)

        val frequencies = arrayOf("Daily", "Weekly", "Monthly")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, frequencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrequency.adapter = adapter

        btnSave.setOnClickListener { saveHabit() }
    }

    private fun saveHabit() {
        val name = etName.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val frequency = spinnerFrequency.selectedItem.toString()

        if (name.isEmpty()) {
            Toast.makeText(context, "Please enter a habit name", Toast.LENGTH_SHORT).show()
            return
        }

        val token = TokenManager.getToken(requireContext())
        if (token == null) {
            Toast.makeText(context, "Please login first", Toast.LENGTH_SHORT).show()
            return
        }

        showLoading(true)

        val request = CreateHabitRequest(name, description.ifEmpty { null }, frequency)
        RetrofitClient.apiService.createHabit("Bearer $token", request)
            .enqueue(object : Callback<Habit> {
                override fun onResponse(call: Call<Habit>, response: Response<Habit>) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Habit created!", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(context, "Failed to create habit", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Habit>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        btnSave.isEnabled = !show
    }
}
