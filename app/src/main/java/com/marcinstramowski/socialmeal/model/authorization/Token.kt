package com.marcinstramowski.socialmeal.model.authorization

import com.google.gson.annotations.SerializedName

/**
 * Necessary token data
 */
data class Token(
    @SerializedName("token") val token: String,
    @SerializedName("expiry") val expires: Long
)