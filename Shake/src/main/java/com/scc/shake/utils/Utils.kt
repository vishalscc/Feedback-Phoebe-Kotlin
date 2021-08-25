package com.scc.shake.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ApplicationInfo
import android.graphics.Paint
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.MetricAffectingSpan
import android.text.TextPaint

/**
 * Utility class
 */
object Utils {
    fun getToken(context: Context): String? {
        var url: String? = ""
        try {
            val packageManager = context.packageManager
            val info =
                packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            if (info != null && info.metaData != null) {
                url = info.metaData["FEEDBACK_PHOEBE_TOKEN"] as String?
            }
        } catch (e: PackageManager.NameNotFoundException) {
            // No metadata found
        }
        return url
    }

    fun typeface(typeface: Typeface?, string: CharSequence?): SpannableString {
        val s = SpannableString(string)
        s.setSpan(TypefaceSpan(typeface), 0, s.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return s
    }

    class TypefaceSpan(private val typeface: Typeface?) : MetricAffectingSpan() {
        override fun updateDrawState(tp: TextPaint) {
            tp.typeface = typeface
            tp.flags = tp.flags or Paint.SUBPIXEL_TEXT_FLAG
        }

        override fun updateMeasureState(p: TextPaint) {
            p.typeface = typeface
            p.flags = p.flags or Paint.SUBPIXEL_TEXT_FLAG
        }
    }
}