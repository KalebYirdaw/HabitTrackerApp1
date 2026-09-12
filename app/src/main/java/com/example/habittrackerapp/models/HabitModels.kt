package com.example.habittrackerapp.models

data class Habit(
    val id: String = "",
    val name: String = "",
    val description: String? = null,
    val frequency: String = "Daily",
    val isActive: Boolean = true,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val createdDate: String = "",
    val updatedAt: String = "",
    val userId: String = ""
)

data class CreateHabitRequest(
    val name: String,
    val description: String?,
    val frequency: String
)

data class UpdateHabitRequest(
    val name: String,
    val description: String?,
    val frequency: String,
    val isActive: Boolean
)

data class HabitCompletion(
    val id: String = "",
    val habitId: String = "",
    val date: String = "",
    val completed: Boolean = false,
    val updatedAt: String = ""
)

data class HabitCompletionRequest(
    val date: String,
    val completed: Boolean
)