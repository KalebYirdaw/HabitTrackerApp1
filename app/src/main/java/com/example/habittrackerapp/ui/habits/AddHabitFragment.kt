package com.example.habittrackerapp.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R

class AddHabitFragment : Fragment() {

    private lateinit var rvPresetHabits: RecyclerView

    private val presetHabits = listOf(

        PresetHabit(
            name = "Walk",
            icon = "🚶"
        ),

        PresetHabit(
            name = "Sleep",
            icon = "😴"
        ),

        PresetHabit(
            name = "Drink Water",
            icon = "💧"
        ),

        PresetHabit(
            name = "Meditation",
            icon = "🧘"
        ),

        PresetHabit(
            name = "Run",
            icon = "🏃"
        ),

        PresetHabit(
            name = "Cycle",
            icon = "🚴"
        ),

        PresetHabit(
            name = "Workout",
            icon = "🏋️"
        ),

        PresetHabit(
            name = "Swim",
            icon = "🏊"
        ),

        PresetHabit(
            name = "Study",
            icon = "📚"
        ),

        PresetHabit(
            name = "Reading",
            icon = "📖"
        ),

        PresetHabit(
            name = "Soccer",
            icon = "⚽"
        ),

        PresetHabit(
            name = "Add New Habit",
            icon = "➕"
        )
    )

    data class PresetHabit(
        val name: String,
        val icon: String
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_add_habit,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        rvPresetHabits =
            view.findViewById(
                R.id.rvPresetHabits
            )

        setupPresets()
    }

    private fun setupPresets() {

        rvPresetHabits.layoutManager =
            LinearLayoutManager(
                requireContext()
            )

        rvPresetHabits.adapter =
            object :
                RecyclerView.Adapter<PresetViewHolder>() {

                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): PresetViewHolder {

                    val view =
                        LayoutInflater
                            .from(parent.context)
                            .inflate(
                                R.layout.item_preset_habit,
                                parent,
                                false
                            )

                    return PresetViewHolder(view)
                }

                override fun onBindViewHolder(
                    holder: PresetViewHolder,
                    position: Int
                ) {

                    val habit =
                        presetHabits[position]

                    holder.tvName.text =
                        habit.name

                    holder.tvIcon.text =
                        habit.icon

                    holder.itemView.setOnClickListener {

                        val bundle =
                            Bundle().apply {

                                putString(
                                    "habitName",
                                    if (
                                        habit.name ==
                                        "Add New Habit"
                                    ) {
                                        ""
                                    } else {
                                        habit.name
                                    }
                                )

                                putString(
                                    "habitIcon",
                                    habit.icon
                                )

                                putString(
                                    "habitIconName",
                                    if (
                                        habit.name ==
                                        "Add New Habit"
                                    ) {
                                        "Default"
                                    } else {
                                        habit.name
                                    }
                                )
                            }

                        findNavController().navigate(
                            R.id.action_addHabit_to_details,
                            bundle
                        )
                    }
                }

                override fun getItemCount(): Int =
                    presetHabits.size
            }
    }

    class PresetViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val tvIcon: TextView =
            view.findViewById(
                R.id.tvPresetIcon
            )

        val tvName: TextView =
            view.findViewById(
                R.id.tvPresetName
            )
    }
}