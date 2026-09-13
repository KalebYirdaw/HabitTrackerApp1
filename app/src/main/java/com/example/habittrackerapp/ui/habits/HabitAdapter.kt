package com.example.habittrackerapp.ui.habits

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R
import com.example.habittrackerapp.models.Habit

class HabitAdapter(
    private val habits: List<Habit>,
    private val onItemClick: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)

        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: HabitViewHolder,
        position: Int
    ) {
        val habit = habits[position]

        holder.bind(habit)

        holder.itemView.setOnClickListener {
            onItemClick(habit)
        }
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    class HabitViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val habitCard: View =
            itemView.findViewById(R.id.habitCard)

        private val tvIcon: TextView =
            itemView.findViewById(R.id.tvHabitIcon)

        private val tvName: TextView =
            itemView.findViewById(R.id.tvHabitName)

        private val tvFrequency: TextView =
            itemView.findViewById(R.id.tvHabitFrequency)

        private val tvStreak: TextView =
            itemView.findViewById(R.id.tvHabitStreak)

        fun bind(habit: Habit) {

            tvIcon.text = habit.icon ?: "⭐"

            tvName.text = habit.name

            tvFrequency.text = habit.frequency

            tvStreak.text = "🔥 ${habit.currentStreak} days"

            try {
                val color = habit.color ?: "#2196F3"

                habitCard.setBackgroundColor(
                    Color.parseColor(color)
                )

            } catch (e: Exception) {

                habitCard.setBackgroundColor(
                    Color.parseColor("#2196F3")
                )
            }
        }
    }
}