package com.marcinstramowski.socialmeal.model.signIn

import com.google.gson.annotations.SerializedName
import com.marcinstramowski.socialmeal.model.authorization.Token

/**
 * Response sign up data
 */
data class SignInResponse(
    @SerializedName("accessToken") val accessToken: Token,
    @SerializedName("refreshToken") val refreshToken: Token
)