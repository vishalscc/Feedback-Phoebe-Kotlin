package com.scc.shake

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat

/**
 * Configuration for button color so user can match theme with their app
 */
class FeedbackConfig(private val context: Context) {

    fun setSubmitButtonColor(color: Int): FeedbackConfig {
        submitButtonColor = color
        return this
    }

    fun setCancelButtonColor(color: Int): FeedbackConfig {
        cancelButtonColor = color
        return this
    }

    fun setDialogButtonColor(color: Int): FeedbackConfig {
        dialogButtonColor = color
        return this
    }

    fun setSubmitButtonTextColor(color: Int): FeedbackConfig {
        submitButtonTextColor = color
        return this
    }

    fun setCancelButtonTextColor(color: Int): FeedbackConfig {
        cancelButtonTextColor = color
        return this
    }

    fun setFontFromResource(font: Int): FeedbackConfig {
        fontFromResource = ResourcesCompat.getFont(context, font)
        return this
    }

    fun setFontFromAssets(path: String?): FeedbackConfig {
        fontFromAssets = Typeface.createFromAsset(context.assets, path)
        return this
    }

    companion object {
        var submitButtonColor = 0

        var submitButtonTextColor = 0

        var cancelButtonTextColor = 0

        var dialogButtonColor = 0

        var cancelButtonColor = 0

        private const val customFont = 0

        var fontFromAssets: Typeface? = null

        var fontFromResource: Typeface? = null

    }
}