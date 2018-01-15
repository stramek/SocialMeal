package com.marcinstramowski.socialmeal.extensions

import android.support.annotation.StringRes
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import com.marcinstramowski.socialmeal.R

fun TextInputEditText.setCompatError(@StringRes errorStringRes: Int?) {
    if (errorStringRes == null) {
        error = null
    } else {
        val errorImage = ContextCompat.getDrawable(context, R.drawable.ic_error)
        errorImage?.setBounds(0, 0, errorImage.intrinsicWidth, errorImage.intrinsicHeight)
        setError(context.getString(errorStringRes), errorImage)
    }
}