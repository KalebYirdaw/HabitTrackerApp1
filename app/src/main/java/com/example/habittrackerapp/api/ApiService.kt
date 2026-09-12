package com.example.habittrackerapp.api

import com.example.habittrackerapp.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    
    @POST("api/Auth/register")
    fun register(@Body request: RegisterRequest): Call<Unit>
    
    @POST("api/Auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
    
    @GET("api/Habit")
    fun getHabits(@Header("Authorization") token: String): Call<List<Habit>>
    
    @POST("api/Habit")
    fun createHabit(
        @Header("Authorization") token: String,
        @Body request: CreateHabitRequest
    ): Call<Habit>
    
    @PUT("api/Habit/{id}")
    fun updateHabit(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: UpdateHabitRequest
    ): Call<Habit>
    
    @DELETE("api/Habit/{id}")
    fun deleteHabit(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<Unit>
    
    @POST("api/Habit/{habitId}/complete")
    fun completeHabit(
        @Header("Authorization") token: String,
        @Path("habitId") habitId: String,
        @Body request: HabitCompletionRequest
    ): Call<HabitCompletion>
    
    @GET("api/Habit/{habitId}/history")
    fun getHabitHistory(
        @Header("Authorization") token: String,
        @Path("habitId") habitId: String
    ): Call<List<HabitCompletion>>
    
    @GET("api/Users/{userId}")
    fun getUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Call<UserDto>
}
