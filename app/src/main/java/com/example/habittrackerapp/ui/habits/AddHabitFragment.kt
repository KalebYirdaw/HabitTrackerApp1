package com.example.habittrackerapp.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R

class AddHabitFragment : Fragment() {

    private lateinit var rvPresetHabits: RecyclerView

    private val presetHabits = listOf(
        PresetHabit("Walk", android.R.drawable.ic_menu_directions),
        PresetHabit("Sleep", android.R.drawable.ic_lock_idle_alarm),
        PresetHabit("Drink Water", android.R.drawable.ic_menu_gallery),
        PresetHabit("Meditation", android.R.drawable.ic_menu_compass),
        PresetHabit("Run", android.R.drawable.ic_menu_directions),
        PresetHabit("Cycle", android.R.drawable.ic_menu_directions),
        PresetHabit("Workout", android.R.drawable.ic_menu_manage),
        PresetHabit("Swim", android.R.drawable.ic_menu_directions),
        PresetHabit("Add New Habit", android.R.drawable.ic_input_add)
    )

    data class PresetHabit(val name: String, val iconRes: Int)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_habit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvPresetHabits = view.findViewById(R.id.rvPresetHabits)
        setupPresets()
    }

    private fun setupPresets() {
        rvPresetHabits.layoutManager = LinearLayoutManager(requireContext())
        rvPresetHabits.adapter = object : RecyclerView.Adapter<PresetViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresetViewHolder {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.item_preset_habit, parent, false)
                return PresetViewHolder(v)
            }

            override fun onBindViewHolder(holder: PresetViewHolder, position: Int) {
                val habit = presetHabits[position]
                holder.tv.text = habit.name
                holder.iv.setImageResource(habit.iconRes)
                holder.itemView.setOnClickListener {
                    val bundle = Bundle().apply {
                        putString("habitName", if (habit.name == "Add New Habit") "" else habit.name)
                    }
                    findNavController().navigate(R.id.action_addHabit_to_details, bundle)
                }
            }

            override fun getItemCount() = presetHabits.size
        }
    }

    class PresetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tvPresetName)
        val iv: ImageView = view.findViewById(R.id.ivHabitIcon)
    }
}
