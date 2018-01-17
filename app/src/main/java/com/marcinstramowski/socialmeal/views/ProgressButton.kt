package com.marcinstramowski.socialmeal.views

import android.content.Context
import android.content.res.TypedArray
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.marcinstramowski.socialmeal.R
import kotlinx.android.synthetic.main.view_progress_button.view.*

class ProgressButton(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val buttonText: String

    init {
        LayoutInflater.from(context).inflate(R.layout.view_progress_button, this)
        val arr: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton)
        buttonText = arr.getString(R.styleable.ProgressButton_progress_button_text)
        arr.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        button.text = buttonText
    }

    override fun setEnabled(enabled: Boolean) {
        button.isEnabled = enabled
        val color =
            if (enabled) R.color.account_enabled_color else R.color.account_button_disabled_color
        button.setTextColor(ContextCompat.getColor(context, color))
    }

    fun setProcessing(processing: Boolean) {
        button.text = if (processing) null else buttonText
        progressBar.visibility = if (processing) View.VISIBLE else View.INVISIBLE
        isEnabled = !processing
    }

    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }
}