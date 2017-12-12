package com.marcinstramowski.socialmeal.api.auth.model

/**
 * Created by marcinstramowski on 09.12.2017.
 */
data class Token(
        val token: String,
        val expires: Long
)