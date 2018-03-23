package com.marcinstramowski.socialmeal.model.authorization

/**
 * Necessary data to refresh user token
 */
data class RefreshTokenRequest(val token : String)