package com.marcinstramowski.socialmeal.model

import com.google.gson.annotations.SerializedName

/**
 * Necessary data to sign up user
 */
data class SignInRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("deviceId") val deviceId: String,
    @SerializedName("deviceName") val deviceName: String
)