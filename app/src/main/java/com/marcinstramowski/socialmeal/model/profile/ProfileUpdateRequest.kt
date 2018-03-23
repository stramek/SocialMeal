package com.marcinstramowski.socialmeal.model.profile
import com.google.gson.annotations.SerializedName

data class ProfileUpdateRequest(
		@SerializedName("firstName") val firstName: String,
		@SerializedName("surname") val surname: String,
		@SerializedName("description") val description: String,
		@SerializedName("favouriteFoodTypes") val favouriteFoodTypes: List<String>
)