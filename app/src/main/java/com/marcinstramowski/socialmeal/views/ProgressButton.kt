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

/**
 * Created by marcinstramowski on 17.12.2017.
 */
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

    fun setProcessing() {
        button.text = null
        progressBar.visibility = View.VISIBLE
        button.isClickable = false
    }

    fun setProcessingFinished() {
        button.text = buttonText
        progressBar.visibility = View.INVISIBLE
        button.isClickable = true
    }

    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }
}