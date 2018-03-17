package com.marcinstramowski.socialmeal.model.profile
import com.google.gson.annotations.SerializedName

data class UserProfile(
		@SerializedName("id") val id: String,
		@SerializedName("email") val email: String,
		@SerializedName("firstName") val firstName: String,
		@SerializedName("surname") val surname: String,
		@SerializedName("thumbnail") val thumbnail: String,
		@SerializedName("description") val description: String,
		@SerializedName("role") val role: String,
		@SerializedName("rating") val rating: Double,
		@SerializedName("favouriteFoodType") val favouriteFoodType: List<FavouriteFoodType>
)

data class FavouriteFoodType(
		@SerializedName("name") val name: String
)
