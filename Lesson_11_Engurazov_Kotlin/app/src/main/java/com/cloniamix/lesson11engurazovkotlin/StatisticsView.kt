package com.cloniamix.lesson11engurazovkotlin

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import kotlin.collections.ArrayList


class StatisticsView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var dateColor: Int
    private var columnColor: Int

    /*private var animator: ValueAnimator? = null*/

    private val maxColumnsCount = 9

    private val verticalIndent =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.toFloat(), resources.displayMetrics)

    private lateinit var textPaint: Paint
    var textBounds = Rect()

    private val statistics: ArrayList<DayStatistic> = ArrayList()


    init {
        val a: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StatisticsView,
            0, 0
        )

        try {
            dateColor = a.getInteger(R.styleable.StatisticsView_statistics_dateColor, Color.WHITE)
            columnColor = a.getInteger(R.styleable.StatisticsView_statistics_columnColor, Color.WHITE)

        } finally {
            a.recycle()
        }
        init()
    }

    private fun init() {
        textPaint = Paint()

        val spSize = 16
        val scaledSizeInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spSize.toFloat(), resources.displayMetrics
        )
        textPaint.textSize = scaledSizeInPixels
    }

    // Добавляет новый показатель дня, если колличество показателей не превышает максимальное количество,
    // иначе удаляет первый элемент, а затем добавляет новый
    // и перерисовывает вью
    fun setDayStatistic(dayStatistic: DayStatistic) {
        if (statistics.size < maxColumnsCount) {
            statistics.add(dayStatistic)
        } else {
            statistics.remove(statistics[0])
            statistics.add(dayStatistic)
        }

        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val text = "02.05"
        textPaint.getTextBounds(text, 0, text.length, textBounds)

        var maxStatisticValue = 0

        for (dayStatistic in statistics) {
            maxStatisticValue = Math.max(maxStatisticValue, dayStatistic.statisticValue)
        }

        val scaledValueInDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            maxStatisticValue.toFloat(),
            resources.displayMetrics
        )


        val minw = paddingLeft + paddingRight + maxColumnsCount * (textBounds.width() + textBounds.width() / 2)
        val w = View.resolveSizeAndState(minw, widthMeasureSpec, 0)

        val minh = paddingBottom + paddingTop +
                (textBounds.height() + textPaint.fontMetrics.descent.toInt()) * 2 +
                scaledValueInDp.toInt() +
                4 * verticalIndent.toInt()

        val h = View.resolveSizeAndState(minh, heightMeasureSpec, 0)

        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (dayStatistic in statistics) {
            val index = statistics.indexOf(dayStatistic)
            textPaint.flags = Paint.ANTI_ALIAS_FLAG
            textPaint.color = dateColor
            val spSize = 11
            val scaledSizeInPixels = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spSize.toFloat(), resources.displayMetrics
            )
            textPaint.textSize = scaledSizeInPixels
            textPaint.textAlign = Paint.Align.LEFT
            val fm = textPaint.fontMetrics
            val horizontalIndent: Float =
                (width - (statistics.size * textBounds.width())) / (statistics.size + 1).toFloat()

            canvas?.drawText(
                dayStatistic.date,
                horizontalIndent + index * (horizontalIndent + textBounds.width()),
                height - fm.descent - verticalIndent,
                textPaint
            )


            val dpSize = 4
            val valueInDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                statistics[index].statisticValue.toFloat(),
                resources.displayMetrics
            )
            val scaledSizeInDp =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize.toFloat(), resources.displayMetrics)
            textPaint.strokeWidth = scaledSizeInDp
            textPaint.color = columnColor

            canvas?.drawLine(
                horizontalIndent + textBounds.width() / 2 + index * (horizontalIndent + textBounds.width()),
                height - (textBounds.height() + fm.descent + 2 * verticalIndent),
                horizontalIndent + textBounds.width() / 2 + index * (horizontalIndent + textBounds.width()),
                height - (textBounds.height() + fm.descent + 2 * verticalIndent + valueInDp),
                textPaint
            )

            /* textPaint.color = columnColor*/
            textPaint.textAlign = Paint.Align.CENTER

            canvas?.drawText(
                "${statistics[index].statisticValue}",
                horizontalIndent + textBounds.width() / 2 + index * (horizontalIndent + textBounds.width()),
                height - (textBounds.height() + fm.descent + 3 * verticalIndent + valueInDp),
                textPaint
            )
        }
    }

    data class DayStatistic(
        val date: String,
        val statisticValue: Int
    )
}
