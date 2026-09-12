package com.example.habittrackerapp.models

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val expiration: String
)

data class UserDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)