package com.example.lda.utils
import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.view.Gravity
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.lda.R

fun Context.createCardGrid(
    rootLayout: LinearLayout,
    cardDataList: List<Pair<Int, String>>,
    cardPerRow: Int = 3,
    typeface: Typeface? = null,
    cardDiv: Int = R.drawable.card_div
) {
    val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
        setMargins(10, 10, 10, 10)
    }

    cardDataList.chunked(cardPerRow).forEach { rowItems ->
        val rowParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8.dp, 2.dp, 8.dp, 0) // top margin of 10dp, or customize as needed
        }

        val rowLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = rowParams
            weightSum = cardPerRow.toFloat()
        }


        rowItems.forEach { (imageRes, textData) ->
            val cardLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = params
                gravity = Gravity.CENTER
            }

            val imageContainer = LinearLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    100.dp
                )
                gravity = Gravity.CENTER
                background = ContextCompat.getDrawable(this@createCardGrid,cardDiv)
            }

            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(60.dp, 60.dp)
                setImageResource(imageRes)
            }

            val textView = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                gravity = Gravity.CENTER
                text = textData
                setPadding(0, 8, 0, 0)
                setTextColor(ContextCompat.getColor(this@createCardGrid, R.color.primary))
                setTypeface(typeface)
                textSize = 12f
            }

            imageContainer.addView(imageView)
            cardLayout.addView(imageContainer)
            cardLayout.addView(textView)
            rowLayout.addView(cardLayout)
        }

        rootLayout.addView(rowLayout)
    }
}
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

