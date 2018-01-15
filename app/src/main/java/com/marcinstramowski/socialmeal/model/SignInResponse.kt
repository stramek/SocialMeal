package com.marcinstramowski.socialmeal.model

import com.google.gson.annotations.SerializedName

/**
 * Response sign up data
 */
data class SignInResponse(
    @SerializedName("accessToken") val accessToken: Token,
    @SerializedName("refreshToken") val refreshToken: Token
)