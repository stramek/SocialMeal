package com.marcinstramowski.socialmeal.extensions

import android.support.annotation.DrawableRes
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat

fun TextInputEditText.setErrorWithImage(errorText: String, @DrawableRes drawableRes: Int) {
    val errorImage = ContextCompat.getDrawable(context, drawableRes)
    errorImage?.setBounds(0, 0, errorImage.intrinsicWidth, errorImage.intrinsicHeight)
    setError(errorText, errorImage)
}