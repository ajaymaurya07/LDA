package com.example.lda.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import androidx.core.content.res.ResourcesCompat
import com.example.lda.R


class CustomTypeFaceSpan(family: String, private val typeface: Typeface) : TypefaceSpan(family) {

    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeface(ds, typeface)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeface(paint, typeface)
    }

    private fun applyCustomTypeface(paint: TextPaint, tf: Typeface) {
        paint.typeface = tf
        paint.flags = paint.flags or Paint.SUBPIXEL_TEXT_FLAG
    }
}


fun getLabelValueText(
    context: Context,
    label: String,
    value: String?
): SpannableString {
    val montserratBold = ResourcesCompat.getFont(context, R.font.montserrat_bold)
    val text = "$label${if (value.isNullOrBlank()) "N/A" else value}"


    return SpannableString(text).apply {
        montserratBold?.let {
            setSpan(
                CustomTypeFaceSpan("montserrat-bold", it),
                0,
                label.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // Extra bold (optional)
        setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            label.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}
