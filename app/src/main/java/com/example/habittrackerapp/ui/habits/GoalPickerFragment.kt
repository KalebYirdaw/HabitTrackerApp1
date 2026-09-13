package com.example.habittrackerapp.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.R

class GoalPickerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_goal_picker,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.goalOne)
            .setOnClickListener {
                selectGoal(1)
            }

        view.findViewById<View>(R.id.goalTwo)
            .setOnClickListener {
                selectGoal(2)
            }

        view.findViewById<View>(R.id.goalThree)
            .setOnClickListener {
                selectGoal(3)
            }

        view.findViewById<View>(R.id.goalFive)
            .setOnClickListener {
                selectGoal(5)
            }

        view.findViewById<View>(R.id.goalSeven)
            .setOnClickListener {
                selectGoal(7)
            }
    }

    private fun selectGoal(goal: Int) {

        parentFragmentManager.setFragmentResult(
            "goal_picker_result",
            Bundle().apply {
                putInt(
                    "selectedGoal",
                    goal
                )
            }
        )

        findNavController().navigateUp()
    }
}