package com.marcinstramowski.socialmeal.model.profile

import android.content.Context
import android.support.annotation.StringRes
import com.google.gson.annotations.SerializedName
import com.marcinstramowski.socialmeal.R

data class UserProfile(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("description") val description: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("favouriteFoodType") val favouriteFoodType: List<FavouriteFoodType>
)

data class FavouriteFoodType(
    @SerializedName("name") val type: FoodTypes?
)

enum class FoodTypes(@StringRes val nameRes: Int) {

    @SerializedName("Undefined") UNDEFINED(R.string.food_type_undefined),
    @SerializedName("Italian") ITALIAN(R.string.food_type_italian),
    @SerializedName("Polish") POLISH(R.string.food_type_polish);

    companion object {
        fun getNames(context: Context) = values().map { context.getString(it.nameRes) }
    }
}
