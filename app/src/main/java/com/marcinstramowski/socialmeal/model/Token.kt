package com.marcinstramowski.socialmeal.model

import com.google.gson.annotations.SerializedName

/**
 * Necessary token data
 */
data class Token(
        @SerializedName("token") val token: String,
        @SerializedName("expiry") val expires: Long
)