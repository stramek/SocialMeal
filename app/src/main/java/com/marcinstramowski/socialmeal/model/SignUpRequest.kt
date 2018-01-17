package com.marcinstramowski.socialmeal.model

import com.google.gson.annotations.SerializedName

/**
 * Necessary data to register user
 */
data class SignUpRequest(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)